package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.entity.Review;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
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
    public void doGet(RequestService reqService) {
        Boolean failed = reqService.getBool("failed") != null;
        Long productId = reqService.getLong("productId");
        Review review = (Review) reqService.getFlashParameter("review");
        Product product = productService.findById(productId);
        User user = reqService.getUser();
        Review oldReview = reviewService.findByUserIdProductId(user.getId(), product.getId());
        if (oldReview != null) {
            reqService.setRedirectPath("/user/editReview?id=" + oldReview.getId());
        }
        useDefaultRenderPage(reqService);
        if (failed) {
            reqService.setPageAttribute("failed", true);
            reqService.setPageAttribute("review", review);
        }
        reqService.setPageAttribute("product", product);
        reqService.setRenderPage("/pages/user/new-review.jsp");
    }
}