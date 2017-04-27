package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.entity.Review;
import com.gmail.at.sichyuriyy.onlinestore.security.AccessDeniedException;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.service.ReviewService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 4/22/2017.
 */
public class EditReviewController extends Controller {

    private ProductService productService = ServiceLocator.INSTANCE.get(ProductService.class);
    private ReviewService reviewService = ServiceLocator.INSTANCE.get(ReviewService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Boolean failed = reqService.getBool("failed") != null;
        Review review = reviewService.findById(reqService.getLong("id"));
        if (!reqService.getUser().getId().equals(review.getAuthor().getId())) {
            throw new AccessDeniedException();
        }
        Product product = productService.findById(review.getProduct().getId());
        if (failed) {
            reqService.setPageAttribute("failed", true);
        }
        reqService.setPageAttribute("product", product);
        reqService.setPageAttribute("review", review);
        respService.setResponseResolver(new RenderResolver("/pages/user/edit-review.jsp"));
    }
}
