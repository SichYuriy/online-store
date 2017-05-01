package com.gmail.at.sichyuriyy.onlinestore.validation.mapper;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.Review;
import com.gmail.at.sichyuriyy.onlinestore.validation.RequestMapper;

/**
 * Created by Yuriy on 4/22/2017.
 */
public class ReviewRequestMapper implements RequestMapper<Review> {

    @Override
    public Review map(RequestService requestService) {
        Review review = new Review();
        review.setId(requestService.getLong("id"));
        review.setAuthor(requestService.getUser());
        review.setDescription(requestService.getString("description").trim());
        if (review.getDescription() == null || review.getDescription() == "") {
            review.setDescription("-");
        }
        review.setProduct(new Product(requestService.getLong("productId")));
        review.setRating(requestService.getInt("rating").doubleValue());
        return review;
    }
}
