package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 4/10/2017.
 */
public class LoginController extends Controller {

    private AuthService authService = ServiceLocator.INSTANCE.get(AuthService.class);

    @Override
    public void doGet(RequestService reqService) {
        String username = (String) reqService.getFlashParameter("username");
        reqService.setPageAttribute("username", username);
    }

    @Override
    public void doPost(RequestService reqService) {
        String username = reqService.getString("username");
        String password = reqService.getString("password");
        String destination = (String) reqService.getFlashParameter("forbidden_page");

        if (authService.login(reqService.getRequest(), username, password)) {
            if(destination == null) {
                reqService.setRedirectPath("/");
            } else {
                reqService.setRedirectPath(destination);
            }
        } else {
            reqService.setRedirectPath("/login.jsp?failed=true");
            reqService.putFlashParameter("username", username);
        }
    }
}
