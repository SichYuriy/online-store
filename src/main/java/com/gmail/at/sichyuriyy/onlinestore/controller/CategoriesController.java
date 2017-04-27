package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.AjaxRedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
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
    public void doGet(RequestService reqService, ResponseService respService) {
        List<Category> categories = categoryService.findAll();
        respService.setResponseResolver(new RenderResolver("/pages/admin/categories.jsp"));
        reqService.setPageAttribute("categories", categories);
    }

    @Override
    public void doPost(RequestService reqService, ResponseService respService) {
        Category category = new CategoryRequestMapper().map(reqService);

        if (!new CategoryValidator().getValidationStatus(category)) {
            reqService.putFlashParameter("category", category);
            respService.setResponseResolver(new RedirectResolver("/admin/newCategory.jsp?failed=true"));
        } else {
            categoryService.create(category);
            respService.setResponseResolver(new RedirectResolver("/admin/categories"));
        }
    }

    @Override
    public void doDelete(RequestService reqService, ResponseService respService) {
        Long id = reqService.getLong("id");
        if (categoryService.delete(id)) {
            respService.setResponseResolver(
                    new AjaxRedirectResolver("/admin/categories"));
            LogManager.getLogger().info("deleted");
        } else {
            respService.setResponseResolver(
                    new AjaxRedirectResolver("/admin/categories?delete_failed=true"));
        }
    }

    @Override
    public void doPut(RequestService reqService, ResponseService respService) {
        Category category = new CategoryRequestMapper().map(reqService);
        if (!new CategoryValidator().getValidationStatus(category)) {
            reqService.putFlashParameter("category", category);
            respService.setResponseResolver(
                    new RedirectResolver("/admin/editCategory.jsp?failed=true"));
        } else {
            categoryService.update(category);
            respService.setResponseResolver(
                    new RedirectResolver("/admin/categories"));
        }
    }
}
