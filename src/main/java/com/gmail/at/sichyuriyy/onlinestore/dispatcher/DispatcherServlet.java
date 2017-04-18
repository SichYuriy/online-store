package com.gmail.at.sichyuriyy.onlinestore.dispatcher;


import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuriy on 4/6/2017.
 */
public class DispatcherServlet extends HttpServlet {

    public static final Logger LOGGER = LogManager.getLogger(DispatcherServlet.class);


    public static final String PAGE_SUFFIX = ".jsp";

    public static final String FLASH_KEY = "__flash";

    public static final String DESTINATION_KEY = "__destination";

    private Map<String, Controller> urlControllerMap = new HashMap<>();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        dispatch(req, resp);
    }

    private void dispatch(HttpServletRequest req, HttpServletResponse resp) {
        Controller controller = getController(req);
        RequestService requestService = new RequestService(req, resp);
        if (controller == null) {
            sendError(resp, HttpServletResponse.SC_NOT_FOUND);
        } else {
            controller.execute(requestService);
        }

        if (requestService.isRedirect()) {
            tryRedirect(req, resp, requestService);
        } else if (requestService.isRender()) {
            tryRender(req, resp, requestService);
            requestService.clearFlash();
        } else if (requestService.isAjaxRedirect()) {
            tryRedirectAjax(req, resp, requestService);
        }

        ConnectionManager cm = ServiceLocator.INSTANCE.get(ConnectionManager.class);
        if (cm != null) {
            cm.clean();
        }
    }

    public void addMapping(String url, Controller controller) {
        urlControllerMap.put(url, controller);
    }

    private Controller getController(HttpServletRequest req) {
        String path = req.getPathInfo();
        int pageSuffixIndex = path.lastIndexOf(PAGE_SUFFIX);
        int endIndex = pageSuffixIndex  == -1 ? path.length() : pageSuffixIndex;
        String controllerUrl = path.substring(0, endIndex);
        return urlControllerMap.get(controllerUrl);
    }

    private void tryRedirect(HttpServletRequest req, HttpServletResponse resp, RequestService requestService) {
        try {
            //req.getRequestDispatcher(requestService.getRedirectPath()).forward(req, resp);
            resp.sendRedirect(requestService.getRedirectPath());
        } catch (IOException e) {
            LOGGER.warn("An exception happened at redirecting");
        }
    }

    private void tryRender(HttpServletRequest req, HttpServletResponse resp, RequestService requestService) {
        LOGGER.info("tryRender:" + req.getPathInfo());
        try {
            requestService.setPageAttribute("loggedIn", req.getUserPrincipal() != null);
            req.getRequestDispatcher(requestService.getRenderPage()).forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.warn("An exception happened at page rendering phase", e);
        } catch (NullPointerException e) {
            LOGGER.error("NULL POINTER", e);
            throw e;
            //TODO: delete NullPointerException catcher
        }
    }

    private void sendError(HttpServletResponse resp, int status) {
        try {
            resp.sendError(status);
        } catch (IOException e) {
            LOGGER.error("Cannot send error", e);
        }
    }

    private void tryRedirectAjax(HttpServletRequest req, HttpServletResponse resp, RequestService requestService) {
        try {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"redirect\":\"" + requestService.getAjaxRedirectPath() + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
