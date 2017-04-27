package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class NewAdminController extends Controller {

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        String error = (String) reqService.getFlashParameter("error");
        String username = (String) reqService.getFlashParameter("username");
        if (error != null) {
            reqService.setPageAttribute("error", error);
        }
        reqService.setPageAttribute("username", username);
        respService.setResponseResolver(new RenderResolver("/pages/admin/new-admin.jsp"));

    }
}
