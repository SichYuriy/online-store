package com.gmail.at.sichyuriyy.onlinestore.validation.mapper;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.validation.RequestMapper;

/**
 * Created by Yuriy on 4/12/2017.
 */
public class CategoryRequestMapper implements RequestMapper<Category> {

    @Override
    public Category map(RequestService requestService) {
        Category category = new Category();
        category.setId(requestService.getLong("id"));
        category.setTitle(requestService.getString("title"));
        return category;
    }
}
