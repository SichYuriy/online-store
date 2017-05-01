package com.gmail.at.sichyuriyy.onlinestore.domain;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Yuriy on 4/30/2017.
 */
public class ShoppingCart {

    private List<CartItem> cartItems;
    private BigDecimal total;

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }
}
