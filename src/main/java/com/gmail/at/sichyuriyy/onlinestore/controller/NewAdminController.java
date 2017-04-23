package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class NewAdminController extends Controller {

    @Override
    public void doGet(RequestService reqService) {
        String error = (String) reqService.getFlashParameter("error");
        String username = (String) reqService.getFlashParameter("username");
        org.apache.logging.log4j.LogManager.getLogger().info(reqService.getRenderPage());
        if (error != null) {
            reqService.setPageAttribute("error", error);
        }
        reqService.setPageAttribute("username", username);
        reqService.setRenderPage("/pages/admin/new-admin.jsp");
    }
}
