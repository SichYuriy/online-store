package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.apache.logging.log4j.LogManager;

import java.util.List;

/**
 * Created by Yuriy on 4/9/2017.
 */
public class HomeController extends Controller {

    private CategoryService categoryService = ServiceLocator.INSTANCE.get(CategoryService.class);

    @Override
    public void doGet(RequestService reqService) {
        LogManager.getLogger().info("home get request");
        List<Category> categories = categoryService.findAll();
        reqService.setPageAttribute("categories", categories);
        reqService.setRenderPage("/pages/index.jsp");
    }
}
