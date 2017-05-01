package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.CartItem;

import java.util.List;

/**
 * Created by Yuriy on 4/30/2017.
 */
public interface CartItemDao extends Dao<CartItem,Long> {

    List<CartItem> findByUser(Long userId);
    void deleteByUser(Long userId);
    CartItem findByUserProduct(Long userId, Long productId);

}
