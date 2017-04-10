package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import org.apache.logging.log4j.LogManager;

/**
 * Created by Yuriy on 4/9/2017.
 */
public class HomeController extends Controller {

    @Override
    public void doGet(RequestService reqService) {
        LogManager.getLogger().info("home get request");
        reqService.setRenderPage("/pages/index.jsp");
    }
}
