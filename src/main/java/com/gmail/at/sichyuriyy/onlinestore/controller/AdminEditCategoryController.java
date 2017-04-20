package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 4/20/2017.
 */
public class AdminEditCategoryController extends Controller {

    CategoryService categoryService = ServiceLocator.INSTANCE.get(CategoryService.class);

    @Override
    public void doGet(RequestService reqService) {
        Long id = reqService.getLong("id");
        Category category = categoryService.findById(id);
        reqService.setPageAttribute("category", category);
        reqService.setRenderPage("/pages/admin/edit-category.jsp");
    }

}
