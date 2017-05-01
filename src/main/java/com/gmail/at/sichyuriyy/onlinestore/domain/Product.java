package com.gmail.at.sichyuriyy.onlinestore.domain;

import java.math.BigDecimal;
import java.util.Objects;

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
    private String mainImageUrl;
    private Boolean enabled;

    public Product() {
    }

    public Product(Long id) {
        this.id = id;
    }

    public Product(Long id, String title, String description, Category category,
                   BigDecimal price, Integer count, Integer votesCount,
                   Double avgRating, String mainImageUrl, Boolean enabled) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.count = count;
        this.votesCount = votesCount;
        this.avgRating = avgRating;
        this.mainImageUrl = mainImageUrl;
        this.enabled = enabled;
    }

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

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getMainImageUrl() {
        return mainImageUrl;
    }

    public void setMainImageUrl(String mainImageUrl) {
        this.mainImageUrl = mainImageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) &&
                Objects.equals(title, product.title) &&
                Objects.equals(description, product.description) &&
                Objects.equals(category, product.category) &&
                Objects.equals(price, product.price) &&
                Objects.equals(count, product.count) &&
                Objects.equals(votesCount, product.votesCount) &&
                Objects.equals(avgRating, product.avgRating) &&
                Objects.equals(mainImageUrl, product.mainImageUrl) &&
                Objects.equals(enabled, product.enabled);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description,
                category, price, count, votesCount,
                avgRating, mainImageUrl, enabled);
    }
}
