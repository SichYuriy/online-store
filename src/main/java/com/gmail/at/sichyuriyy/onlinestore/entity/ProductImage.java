package com.gmail.at.sichyuriyy.onlinestore.entity;

import sun.nio.cs.US_ASCII;

import java.net.URL;
import java.util.Objects;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class ProductImage {

    private Long id;
    private Product product;
    private String imageUrl;
    private String smallVersionUrl;

    public ProductImage() {
    }

    public ProductImage(Long id) {
        this.id = id;
    }

    public ProductImage(Long id, Product product, String imageUrl, String smallVersionUrl) {
        this.id = id;
        this.product = product;
        this.imageUrl = imageUrl;
        this.smallVersionUrl = smallVersionUrl;
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

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getSmallVersionUrl() {
        return smallVersionUrl;
    }

    public void setSmallVersionUrl(String smallVersionUrl) {
        this.smallVersionUrl = smallVersionUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProductImage image = (ProductImage) o;
        return Objects.equals(id, image.id) &&
                Objects.equals(product, image.product) &&
                Objects.equals(imageUrl, image.imageUrl) &&
                Objects.equals(smallVersionUrl, image.smallVersionUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, product, imageUrl, smallVersionUrl);
    }
}
