package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.Review;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class ReviewMapper implements RowMapper<Review> {

    @Override
    public Review map(ResultSet rs) throws SQLException {
        Review review = new Review();
        review.setId(rs.getLong("id"));
        review.setProduct(new Product(rs.getLong("product_id")));
        review.setAuthor(new User(rs.getLong("author_id")));
        review.setDate(rs.getTimestamp("date"));
        review.setDescription(rs.getString("description"));
        review.setRating(rs.getDouble("rating"));
        return review;
    }
}
