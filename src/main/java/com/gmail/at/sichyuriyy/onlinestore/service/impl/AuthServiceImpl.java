package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
import com.gmail.at.sichyuriyy.onlinestore.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by Yuriy on 4/7/2017.
 */
public class AuthServiceImpl implements AuthService {

    private static final Logger LOGGER = LogManager.getLogger(AuthServiceImpl.class);

    private UserService userService;

    public AuthServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean login(HttpServletRequest request, String username, String password) {
        try {
            request.login(username, password);
            return true;
        } catch (ServletException e) {
            return false;
        }
    }

    @Override
    public void logout(HttpServletRequest request) {
        try {
            request.logout();
        } catch (ServletException e) {
            LOGGER.error("Error occurred when user tried to logout", e);
        }
    }

    @Override
    public boolean registerCustomer(User user) {
        user.setPassword(user.getPassword());
        user.setRoles(Collections.singletonList(Role.CUSTOMER));
        try {
            userService.create(user);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean registerAdministrator(User user) {
        user.setPassword(user.getPassword());
        user.setRoles(Arrays.asList(Role.CUSTOMER, Role.ADMINISTRATOR));
        try {
            userService.create(user);
        } catch (Exception e) {
            user.setId(null);
        }

        return user.getId() != null;
    }

}
