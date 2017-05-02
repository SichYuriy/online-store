package com.gmail.at.sichyuriyy.onlinestore.domain;

/**
 * Created by Yuriy on 4/30/2017.
 */
public class CartItem {

    private Long id;
    private Product product;
    private Integer count;
    private User user;

    public CartItem() {
    }

    public CartItem(Long id, Product product, Integer count, User user) {
        this.id = id;
        this.product = product;
        this.count = count;
        this.user = user;
    }

    public CartItem(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
