package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServlet;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Yuriy on 4/10/2017.
 */
public class LoginController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger(LoginController.class);

    private AuthService authService = ServiceLocator.INSTANCE.get(AuthService.class);

    @Override
    public void doGet(RequestService reqService) {
        Boolean failed = reqService.getBool("failed") != null;
        String username = (String) reqService.getFlashParameter("username");
        useDefaultRenderPage(reqService);
        if (failed) {
            reqService.setPageAttribute("failed", true);
            reqService.setPageAttribute("username", username);
        }

    }

    @Override
    public void doPost(RequestService reqService) {
        LOGGER.info("login request");
        String username = reqService.getString("username");
        String password = reqService.getString("password");
        String destination = (String) reqService.getRequest().getSession().getAttribute(DispatcherServlet.DESTINATION_KEY);


        if (authService.login(reqService.getRequest(), username, password)) {
            LOGGER.info("user: " + username + " login success");
            if(destination == null) {
                reqService.setRedirectPath("/");
            } else {
                reqService.setRedirectPath(destination);
            }
        } else {
            reqService.setRedirectPath("/login.jsp?failed=true");
            reqService.putFlashParameter("username", username);
        }
        reqService.getRequest().getSession().removeAttribute(DispatcherServlet.DESTINATION_KEY);
    }
}
