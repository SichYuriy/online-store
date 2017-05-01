package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.domain.CartItem;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 4/30/2017.
 */
public class CartItemMapper implements RowMapper<CartItem> {


    @Override
    public CartItem map(ResultSet rs) throws SQLException {
        CartItem cartItem = new CartItem();
        cartItem.setId(rs.getLong("id"));
        cartItem.setCount(rs.getInt("count"));
        cartItem.setProduct(new Product(rs.getLong("product_id")));
        cartItem.setUser(new User(rs.getLong("user_id")));
        return cartItem;
    }
}
