package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.entity.Category;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class CategoryMapper implements RowMapper<Category> {

    @Override
    public Category map(ResultSet rs) throws SQLException {
        Category category = new Category();
        category.setId(rs.getLong("id"));
        category.setTitle(rs.getString("title"));
        return category;
    }
}
