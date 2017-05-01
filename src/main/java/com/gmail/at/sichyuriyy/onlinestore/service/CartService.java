package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.domain.CartItem;
import com.gmail.at.sichyuriyy.onlinestore.domain.ShoppingCart;

import java.util.List;

/**
 * Created by Yuriy on 4/30/2017.
 */
public interface CartService {

    ShoppingCart findByUser(Long userId);

    boolean addProduct(Long userId, Long productId);
    boolean removeProduct(Long userId, Long productId);

    void clearCart(Long userId);

    List<CartItem> findCartItemsByUser(Long userId);
}
