package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.domain.Category;

/**
 * Created by Yuriy on 4/15/2017.
 */
public class AdminNewCategoryController extends Controller {

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Category category = (Category) reqService.getFlashParameter("category");
        reqService.setPageAttribute("category", category);
        respService.setResponseResolver(new RenderResolver("/pages/admin/new-category.jsp"));
    }
}
