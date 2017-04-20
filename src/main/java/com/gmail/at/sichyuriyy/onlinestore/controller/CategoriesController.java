package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.TransactionFailedException;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import com.gmail.at.sichyuriyy.onlinestore.validation.mapper.CategoryRequestMapper;
import com.gmail.at.sichyuriyy.onlinestore.validation.validator.CategoryValidator;
import com.sun.media.jfxmedia.logging.Logger;
import org.apache.logging.log4j.LogManager;
import sun.rmi.runtime.Log;

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
            reqService.putFlashParameter("category", category);
            reqService.setRedirectPath("/admin/newCategory.jsp?failed=true");
        } else {
            categoryService.create(category);
            reqService.setRedirectPath("/admin/categories");
        }
    }

    @Override
    public void doDelete(RequestService reqService) {
        Long id = reqService.getLong("id");
        if (categoryService.delete(id)) {
            reqService.setAjaxRedirectPath("/admin/categories");
            LogManager.getLogger().info("deleted");
        } else {
            reqService.setAjaxRedirectPath("/admin/categories?delete_failed=true");
        }
    }

    @Override
    public void doPut(RequestService reqService) {
        Category category = new CategoryRequestMapper().map(reqService);
        if (!new CategoryValidator().getValidationStatus(category)) {
            reqService.putFlashParameter("category", category);
            reqService.setRedirectPath("/admin/editCategory.jsp?failed=true");
        } else {
            categoryService.update(category);
            reqService.setRedirectPath("/admin/categories");
        }
    }
}
