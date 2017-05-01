package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.domain.Category;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.CategoryDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;

/**
 * Created by Yuriy on 4/9/2017.
 */
public class CategoryServiceImpl extends AbstractCrudService<Category, Long> implements CategoryService {

    private CategoryDao categoryDao;

    public CategoryServiceImpl(CategoryDao categoryDao) {
        this.categoryDao = categoryDao;
    }

    @Override
    public Dao<Category, Long> getBackingDao() {
        return categoryDao;
    }

    @Override
    public void create(Category category) {
        Long id = categoryDao.create(category);
        category.setId(id);
    }


}
