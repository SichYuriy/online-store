package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.AjaxRedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Review;
import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.security.AccessDeniedException;
import com.gmail.at.sichyuriyy.onlinestore.service.ReviewService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import com.gmail.at.sichyuriyy.onlinestore.validation.mapper.ReviewRequestMapper;
import com.gmail.at.sichyuriyy.onlinestore.validation.validator.ReviewValidator;
import org.apache.logging.log4j.LogManager;

import java.util.logging.Logger;

/**
 * Created by Yuriy on 4/22/2017.
 */
public class ReviewsController extends Controller {

    private ReviewService reviewService = ServiceLocator.INSTANCE.get(ReviewService.class);


    @Override
    public void doPost(RequestService reqService, ResponseService respService) {
        Review review = new ReviewRequestMapper().map(reqService);
        boolean validationStatus = new ReviewValidator().getValidationStatus(review);

        if (!validationStatus) {
            reqService.putFlashParameter("review", review);
            respService.setResponseResolver(
                    new RedirectResolver("/user/newReview?failed=true&productId="
                            + review.getProduct().getId()));
            return;
        }
        checkUser(reqService, review);
        reviewService.create(review);
        respService.setResponseResolver(
                new RedirectResolver("/product?id=" + review.getProduct().getId()));
    }

    @Override
    public void doPut(RequestService reqService, ResponseService respService) {
        LogManager.getLogger().info("update ");
        Review review = new ReviewRequestMapper().map(reqService);
        boolean validationStatus = new ReviewValidator().getValidationStatus(review);
        if (!validationStatus) {
            LogManager.getLogger().info("validation failed ");

            reqService.putFlashParameter("review", review);
            respService.setResponseResolver(
                    new RedirectResolver("/user/editReview?failed=true&id=" + review.getId()));
            return;
        }
        checkUser(reqService, review);
        reviewService.update(review);
        respService.setResponseResolver(new RedirectResolver("/product?id=" + review.getProduct().getId()));
    }

    @Override
    public void doDelete(RequestService reqService, ResponseService respService) {
        Long reviewId = reqService.getLong("id");

        Review review = reviewService.findById(reviewId);
        User user = reqService.getUser();

        if (!review.getAuthor().getId().equals(user.getId())
                && !reqService.getRequest().isUserInRole(Role.ADMINISTRATOR.name())) {
            throw new AccessDeniedException();
        }

        reviewService.delete(reviewId);
        respService.setResponseResolver(
                new AjaxRedirectResolver("/product.jsp?id=" + review.getProduct().getId()));
    }

    private void checkUser(RequestService reqService, Review review) {
        if (!reqService.getUser().getId().equals(review.getAuthor().getId())) {
            throw new AccessDeniedException();
        }
    }
}
