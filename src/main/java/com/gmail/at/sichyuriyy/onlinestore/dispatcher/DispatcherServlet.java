package com.gmail.at.sichyuriyy.onlinestore.dispatcher;


import com.gmail.at.sichyuriyy.onlinestore.dispatcher.util.ControllerUtil;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.util.UrlUtil;
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

    public static final String METHOD_PARAM = "__method";


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
        ConnectionManager cm = ServiceLocator.INSTANCE.get(ConnectionManager.class);
        try{
            Controller controller = ControllerResolver.INSTANCE.getController(req.getPathInfo());

            RequestService requestService = new RequestService(req);
            ResponseService responseService = new ResponseService(req, resp);

            if (controller == null) {
                sendError(resp, HttpServletResponse.SC_NOT_FOUND);
            } else {
                ControllerUtil.service(controller, requestService, responseService);
            }

            responseService.resolveResponse();

        } finally {
            if (cm != null) {
                cm.clean();
            }
        }
    }

    public void addMapping(String url, Controller controller) {
        ControllerResolver.INSTANCE.addMapping(url, controller);
    }

    private void sendError(HttpServletResponse resp, int status) {
        try {
            resp.sendError(status);
        } catch (IOException e) {
            LOGGER.error("Cannot send error", e);
        }
    }

}
