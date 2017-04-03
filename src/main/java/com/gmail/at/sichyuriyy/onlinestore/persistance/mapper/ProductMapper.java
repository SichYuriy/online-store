package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class ProductMapper implements RowMapper<Product> {
    @Override
    public Product map(ResultSet rs) throws SQLException {
        Product product = new Product();
        product.setId(rs.getLong("id"));
        product.setCount(rs.getInt("count"));
        product.setCategory(new Category(rs.getLong("category_id")));
        product.setAvgRating(rs.getDouble("avg_rating"));
        product.setDescription(rs.getString("description"));
        product.setEnabled(rs.getBoolean("enabled"));
        product.setMainImageUrl(rs.getString("main_image_url"));
        product.setTitle(rs.getString("title"));
        product.setPrice(rs.getBigDecimal("price"));
        product.setVotesCount(rs.getInt("votes_count"));
        return product;
    }

}
