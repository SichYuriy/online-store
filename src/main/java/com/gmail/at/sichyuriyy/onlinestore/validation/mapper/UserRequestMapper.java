package com.gmail.at.sichyuriyy.onlinestore.validation.mapper;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.validation.RequestMapper;

/**
 * Created by Yuriy on 4/21/2017.
 */
public class UserRequestMapper implements RequestMapper<User> {
    @Override
    public User map(RequestService requestService) {
        User user = new User();
        user.setLogin(requestService.getString("username").trim());
        user.setPassword(requestService.getString("password").trim());
        user.setBlackList(requestService.getBool("blackList"));

        return user;
    }
}
