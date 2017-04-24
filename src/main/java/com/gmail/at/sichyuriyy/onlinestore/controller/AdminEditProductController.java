package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 4/24/2017.
 */
public class AdminEditProductController extends Controller {

    private ProductService productService = ServiceLocator.INSTANCE.get(ProductService.class);

    private CategoryService categoryService = ServiceLocator.INSTANCE.get(CategoryService.class);

    @Override
    public void doGet(RequestService reqService) {
        Product product = productService.findById(reqService.getLong("id"));
        Boolean failed = reqService.getBool("failed") != null;
        Category category = categoryService.findById(product.getCategory().getId());
        if (failed) {
            product = (Product) reqService.getFlashParameter("product");
            reqService.setPageAttribute("failed", true);
        }
        reqService.setPageAttribute("product", product);
        reqService.setPageAttribute("category", category);
        reqService.setRenderPage("/pages/admin/edit-product.jsp");
    }
}