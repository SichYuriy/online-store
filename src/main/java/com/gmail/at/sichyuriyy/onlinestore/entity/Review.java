package com.gmail.at.sichyuriyy.onlinestore.entity;

import java.sql.DataTruncation;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Objects;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class Review {

    private Long id;
    private User author;
    private Product product;
    private Double rating;
    private String description;
    private Timestamp date;

    public Review() {
    }

    public Review(Long id) {
        this.id = id;
    }

    public Review(Long id, User author, Product product, Double rating, String description, Timestamp date) {
        this.id = id;
        this.author = author;
        this.product = product;
        this.rating = rating;
        this.description = description;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Review review = (Review) o;
        return Objects.equals(id, review.id) &&
                Objects.equals(author, review.author) &&
                Objects.equals(product, review.product) &&
                Objects.equals(rating, review.rating) &&
                Objects.equals(description, review.description) &&
                Objects.equals(date, review.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, product, rating, description, date);
    }
}
