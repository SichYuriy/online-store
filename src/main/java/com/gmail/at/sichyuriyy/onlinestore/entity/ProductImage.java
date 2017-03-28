package com.gmail.at.sichyuriyy.onlinestore.entity;

import java.net.URL;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class ProductImage {

    private Long id;
    private Product product;
    private URL imageUrl;

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

    public URL getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(URL imageUrl) {
        this.imageUrl = imageUrl;
    }
}
