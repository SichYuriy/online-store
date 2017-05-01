package com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServlet;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuriy on 4/28/2017.
 */
public class RenderResolver implements  ResponseResolver {

    private static final Logger LOGGER = LogManager.getLogger(RenderResolver.class);

    private String renderPage;

    public RenderResolver(String renderPage) {
        this.renderPage = renderPage;
    }

    @Override
    public void resolve(HttpServletRequest req, HttpServletResponse resp) {
        try {
            req.setAttribute("loggedIn", req.getUserPrincipal() != null);
            req.getRequestDispatcher(renderPage).forward(req, resp);
        } catch (ServletException | IOException e) {
            LOGGER.warn("An exception happened at page rendering phase", e);
        } catch (NullPointerException e) {
            LOGGER.error("NULL POINTER", e);
            throw e;
            //TODO: delete NullPointerException catcher
        }
        clearFlash(req);
    }


    private void clearFlash(HttpServletRequest request) {
        Map<String, Object> flash =
                (Map<String, Object>) request.getSession().getAttribute(DispatcherServlet.FLASH_KEY);
        if(flash == null) {
            flash = new HashMap<>();
        }
        flash.clear();
        request.getSession().setAttribute(DispatcherServlet.FLASH_KEY, flash);
    }
}
