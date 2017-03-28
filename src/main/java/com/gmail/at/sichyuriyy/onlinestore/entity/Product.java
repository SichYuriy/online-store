package com.gmail.at.sichyuriyy.onlinestore.entity;

import java.math.BigDecimal;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class Product {

    private Long id;
    private String title;
    private String description;
    private Category category;
    private BigDecimal price;
    private Integer count;
    private Integer votesCount;
    private Double avgRating;
    private ProductImage mainImage;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getVotesCount() {
        return votesCount;
    }

    public void setVotesCount(Integer votesCount) {
        this.votesCount = votesCount;
    }

    public Double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(Double avgRating) {
        this.avgRating = avgRating;
    }

    public ProductImage getMainImage() {
        return mainImage;
    }

    public void setMainImage(ProductImage mainImage) {
        this.mainImage = mainImage;
    }
}
