package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.Review;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;
import com.gmail.at.sichyuriyy.onlinestore.service.ReviewService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 4/22/2017.
 */
public class NewReviewController extends Controller {

    private ProductService productService = ServiceLocator.INSTANCE.get(ProductService.class);
    private ReviewService reviewService = ServiceLocator.INSTANCE.get(ReviewService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Boolean failed = reqService.getBool("failed") != null;
        Long productId = reqService.getLong("productId");
        Review review = (Review) reqService.getFlashParameter("review");
        Product product = productService.findById(productId);
        User user = reqService.getUser();
        Review oldReview = reviewService.findByUserIdProductId(user.getId(), product.getId());
        if (oldReview != null) {
            respService.setResponseResolver(new RedirectResolver("/user/editReview?id=" + oldReview.getId()));
        }
        if (failed) {
            reqService.setPageAttribute("failed", true);
            reqService.setPageAttribute("review", review);
        }
        reqService.setPageAttribute("product", product);
        respService.setResponseResolver(new RenderResolver("/pages/user/new-review.jsp"));
    }
}