package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;

/**
 * Created by Yuriy on 4/9/2017.
 */
public class RedirectController extends Controller {

    private final String path;

    public RedirectController(String path) {
        this.path = path;
    }

    @Override
    public void doAny(RequestService reqService, ResponseService respService) {
        respService.setResponseResolver(new RedirectResolver(path));
    }
}
