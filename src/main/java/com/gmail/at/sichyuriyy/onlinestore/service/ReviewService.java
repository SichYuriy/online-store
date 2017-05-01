package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.domain.Review;

import java.util.List;

/**
 * Created by Yuriy on 4/22/2017.
 */
public interface ReviewService extends CrudService<Review, Long> {

    List<Review> findByProductId(Long productId);
    Review findByUserIdProductId(Long userId, Long productId);

    boolean canVote(Long userId);
}
