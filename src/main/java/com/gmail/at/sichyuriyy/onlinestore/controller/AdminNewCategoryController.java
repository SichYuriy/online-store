package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import org.apache.logging.log4j.LogManager;

/**
 * Created by Yuriy on 4/15/2017.
 */
public class AdminNewCategoryController extends Controller {

    @Override
    public void doGet(RequestService reqService) {
        Category category = (Category) reqService.getFlashParameter("category");
        reqService.setPageAttribute("category", category);
        reqService.setRenderPage("/pages/admin/new-category.jsp");
    }
}
