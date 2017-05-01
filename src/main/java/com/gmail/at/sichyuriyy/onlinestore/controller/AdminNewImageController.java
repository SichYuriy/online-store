package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.domain.Category;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.ProductImage;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.ProductServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 5/1/2017.
 */
public class AdminNewImageController extends Controller {

    private ProductService productService = ServiceLocator.INSTANCE.get(ProductService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Boolean failed = reqService.getBool("failed") != null;
        Long categoryId = reqService.getLong("productId");
        ProductImage productImage = (ProductImage) reqService.getFlashParameter("productImage");
        Product product = productService.findById(categoryId);
        if (failed) {
            reqService.setPageAttribute("failed", true);
            reqService.setPageAttribute("productImage", productImage);
        }
        reqService.setPageAttribute("product", product);
        respService.setResponseResolver(new RenderResolver("/pages/admin/new-productImage.jsp"));

    }
}
