package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.domain.Category;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 4/24/2017.
 */
public class AdminNewProductController extends Controller {


    private CategoryService categoryService = ServiceLocator.INSTANCE.get(CategoryService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Boolean failed = reqService.getBool("failed") != null;
        Long categoryId = reqService.getLong("categoryId");
        Product product = (Product) reqService.getFlashParameter("product");
        Category category = categoryService.findById(categoryId);
        if (failed) {
            reqService.setPageAttribute("failed", true);
            reqService.setPageAttribute("product", product);
        }
        reqService.setPageAttribute("category", category);
        respService.setResponseResolver(new RenderResolver("/pages/admin/new-product.jsp"));
    }
}
