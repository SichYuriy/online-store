package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.entity.ProductImage;

import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class ProductImageMapper implements RowMapper<ProductImage> {
    @Override
    public ProductImage map(ResultSet rs) throws SQLException {
        ProductImage image = new ProductImage();
        image.setId(rs.getLong("id"));
        image.setProduct(new Product(rs.getLong("product_id")));
        image.setImageUrl(rs.getString("image_url"));
        image.setSmallVersionUrl(rs.getString("small_image_url"));
        return image;
    }
}
