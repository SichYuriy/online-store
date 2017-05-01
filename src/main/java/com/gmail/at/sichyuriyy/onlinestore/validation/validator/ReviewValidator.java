package com.gmail.at.sichyuriyy.onlinestore.validation.validator;

import com.gmail.at.sichyuriyy.onlinestore.domain.Review;
import com.gmail.at.sichyuriyy.onlinestore.validation.EntityValidator;

/**
 * Created by Yuriy on 4/22/2017.
 */
public class ReviewValidator implements EntityValidator<Review> {

    @Override
    public boolean getValidationStatus(Review review) {

        return review.getDescription() != null
                && review.getAuthor() != null
                && review.getAuthor().getId() != null
                && review.getRating() != null
                && review.getRating() >= 0
                && review.getRating() <= 5
                && review.getProduct() != null
                && review.getProduct().getId() != null;
    }
}
