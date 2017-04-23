package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDynamicFilter;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;

import java.util.List;

/**
 * Created by Yuriy on 4/21/2017.
 */
public class ProductServiceImpl extends AbstractCrudService<Product, Long> implements ProductService {

    private ProductDao productDao;

    public ProductServiceImpl(ProductDao productDao) {
        this.productDao = productDao;
    }

    @Override
    public void create(Product product) {
        Long id = productDao.create(product);
        product.setId(id);
    }

    @Override
    public List<Product> findByDynamicFilter(ProductDynamicFilter filter) {
        return null;
    }

    @Override
    public Dao<Product, Long> getBackingDao() {
        return productDao;
    }
}
