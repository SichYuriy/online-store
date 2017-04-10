package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.entity.User;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yuriy on 4/7/2017.
 */
public interface AuthService {

    boolean login(HttpServletRequest request, String username, String password);
    void logout(HttpServletRequest request);
    boolean registerCustomer(User user);
    boolean registerAdministrator(User user);
}
