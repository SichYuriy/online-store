package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Created by Yuriy on 4/10/2017.
 */
public class LogoutController extends Controller {

    private static final Logger LOGGER = LogManager.getLogger(LogoutController.class);

    private AuthService authService = ServiceLocator.INSTANCE.get(AuthService.class);

    @Override
    public void doPost(RequestService reqService) {
        authService.logout(reqService.getRequest());
        reqService.setRedirectPath("/login");
    }
}
