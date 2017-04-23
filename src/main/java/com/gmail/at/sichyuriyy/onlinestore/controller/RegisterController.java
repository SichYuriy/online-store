package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import com.gmail.at.sichyuriyy.onlinestore.validation.mapper.UserRequestMapper;
import com.gmail.at.sichyuriyy.onlinestore.validation.validator.UserValidator;

import java.util.logging.LogManager;

/**
 * Created by Yuriy on 4/21/2017.
 */
public class RegisterController extends Controller {

    AuthService authService = ServiceLocator.INSTANCE.get(AuthService.class);

    @Override
    public void doGet(RequestService reqService) {

        String error = (String) reqService.getFlashParameter("error");
        String username = (String) reqService.getFlashParameter("username");
        useDefaultRenderPage(reqService);
        org.apache.logging.log4j.LogManager.getLogger().info(reqService.getRenderPage());
        if (error != null) {
            reqService.setPageAttribute("error", error);
        }
        reqService.setPageAttribute("username", username);
    }

    @Override
    public void doPost(RequestService reqService) {
        User user = new UserRequestMapper().map(reqService);
        boolean  validationStatus = new UserValidator().getValidationStatus(user);
        if (!validationStatus) {
            reqService.putFlashParameter("error", "register.error.validation");
            reqService.putFlashParameter("username", user.getLogin());
            reqService.setRedirectPath("/login.jsp?failed=true");
            return;
        }

        if(!authService.registerCustomer(user)) {
            reqService.putFlashParameter("error", "register.error.username");
            reqService.setRedirectPath("/register.jsp?failed=true");
            return;
        }
        reqService.setRedirectPath("/login.jsp");
    }
}
