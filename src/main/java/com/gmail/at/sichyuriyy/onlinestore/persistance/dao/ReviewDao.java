package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.entity.Review;

import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface ReviewDao extends Dao<Review, Long> {

    List<Review> findByProduct(Long productId);
    Review findByUserIdProductId(Long userId, Long productId);
}
