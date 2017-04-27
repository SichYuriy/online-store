package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.entity.*;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductImageService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.service.ReviewService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.util.List;

/**
 * Created by Yuriy on 4/21/2017.
 */
public class ProductController extends Controller {

    private ProductService productService = ServiceLocator.INSTANCE.get(ProductService.class);
    private ReviewService reviewService = ServiceLocator.INSTANCE.get(ReviewService.class);
    private ProductImageService productImageService = ServiceLocator.INSTANCE.get(ProductImageService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Long id = reqService.getLong("id");
        Product product = productService.findById(id);
        User user = reqService.getUser();
        Review oldReview = user == null ? null : reviewService.findByUserIdProductId(user.getId(), id);
        boolean canVote = user != null && reviewService.canVote(user.getId());
        if (product.getAvgRating() == null) {
            product.setAvgRating(0.0);
        }

        List<Review> reviews = reviewService.findByProductId(id);
        List<ProductImage> productImages = productImageService.findByProductId(id);

        reqService.setPageAttribute("product", product);

        reqService.setPageAttribute("canVote", canVote);

        reqService.setPageAttribute("reviews", reviews);

        reqService.setPageAttribute("admin", reqService.getRequest().isUserInRole(Role.ADMINISTRATOR.name()));

        reqService.setPageAttribute("productImages", productImages);

        reqService.setPageAttribute("oldReview", oldReview);

        useDefaultRenderPage(reqService, respService);
    }
}
