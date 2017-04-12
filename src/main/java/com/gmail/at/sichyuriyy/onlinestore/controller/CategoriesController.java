package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import com.gmail.at.sichyuriyy.onlinestore.validation.mapper.CategoryRequestMapper;
import com.gmail.at.sichyuriyy.onlinestore.validation.validator.CategoryValidator;
import org.apache.logging.log4j.LogManager;

import java.util.List;

/**
 * Created by Yuriy on 4/8/2017.
 */
public class CategoriesController extends Controller {

    private CategoryService categoryService = ServiceLocator.INSTANCE.get(CategoryService.class);

    @Override
    public void doGet(RequestService reqService) {
        List<Category> categories = categoryService.findAll();
        reqService.setRenderPage("/pages/admin/categories.jsp");
        reqService.setPageAttribute("categories", categories);
    }

    @Override
    public void doPost(RequestService reqService) {
        Category category = new CategoryRequestMapper().map(reqService);
        if (!new CategoryValidator().getValidationStatus(category)) {
            reqService.putFlashParameter("title", category.getTitle());
            reqService.setRedirectPath("/admin/new_category.jsp?failed=true");
        } else {
            categoryService.create(category);
            reqService.setRedirectPath("/admin/categories");
        }
    }
}
