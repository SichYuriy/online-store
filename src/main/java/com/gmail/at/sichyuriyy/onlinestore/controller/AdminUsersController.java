package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.UserDao;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
import com.gmail.at.sichyuriyy.onlinestore.service.UserService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import com.gmail.at.sichyuriyy.onlinestore.validation.mapper.UserRequestMapper;
import com.gmail.at.sichyuriyy.onlinestore.validation.validator.UserValidator;

import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class AdminUsersController extends Controller {

    private UserService userService = ServiceLocator.INSTANCE.get(UserService.class);
    private AuthService authService = ServiceLocator.INSTANCE.get(AuthService.class);


    @Override
    public void doGet(RequestService reqService) {
        List<User> users = userService.findAll();

        reqService.setPageAttribute("users", users);
        reqService.setRenderPage("/pages/admin/users.jsp");
    }



    @Override
    public void doPut(RequestService reqService) {
        Long id = reqService.getLong("id");
        Boolean blackList = reqService.getBool("blackList");
        userService.changeBlackListStatus(id, blackList);
        reqService.setAjaxRedirectPath("/admin/users");
    }

    @Override
    public void doPost(RequestService reqService) {
        User user = new UserRequestMapper().map(reqService);
        boolean  validationStatus = new UserValidator().getValidationStatus(user);
        if (!validationStatus) {
            reqService.putFlashParameter("error", "register.error.validation");
            reqService.putFlashParameter("username", user.getLogin());
            reqService.setRedirectPath("/admin/newAdmin.jsp?failed=true");
            return;
        }

        if(!authService.registerAdministrator(user)) {
            reqService.putFlashParameter("error", "register.error.username");
            reqService.setRedirectPath("/admin/newAdmin.jsp?failed=true");
            return;
        }
        reqService.setRedirectPath("/admin/users");
    }
}
