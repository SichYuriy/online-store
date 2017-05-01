package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.ProductImage;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductImageService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import com.gmail.at.sichyuriyy.onlinestore.validation.mapper.ProductImageMapper;
import com.gmail.at.sichyuriyy.onlinestore.validation.validator.ProductImageValidator;

import java.util.List;

/**
 * Created by Yuriy on 5/1/2017.
 */
public class ProductImagesController extends Controller {

    private ProductService productService = ServiceLocator.INSTANCE.get(ProductService.class);
    private ProductImageService productImageService = ServiceLocator.INSTANCE.get(ProductImageService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Long productId = reqService.getLong("productId");

        Product product = productService.findById(productId);
        List<ProductImage> images = productImageService.findByProductId(productId);

        reqService.setPageAttribute("product", product);
        reqService.setPageAttribute("images", images);
        respService.setResponseResolver(new RenderResolver("/pages/admin/images.jsp"));
    }

    @Override
    public void doPost(RequestService reqService, ResponseService respService) {
        ProductImage productImage = new ProductImageMapper().map(reqService);
        boolean validationStatus = new ProductImageValidator().getValidationStatus(productImage);

        if (!validationStatus) {
            reqService.putFlashParameter("productImage", productImage);
            respService.setResponseResolver(
                    new RedirectResolver("/admin/newProductImage?failed=true&productId="
                            + productImage.getProduct().getId()));
            return;
        }

        productImageService.create(productImage);

        respService.setResponseResolver(
                new RedirectResolver("/admin/images?productId=" + productImage.getProduct().getId())
        );
    }

    @Override
    public void doDelete(RequestService reqService, ResponseService respService) {
        Long productImageId = reqService.getLong("productImageId");
        productImageService.delete(productImageId);
    }


}
