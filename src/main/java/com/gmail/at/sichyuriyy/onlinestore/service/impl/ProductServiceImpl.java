package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDynamicFilter;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.Transaction;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductService;

import java.util.List;

/**
 * Created by Yuriy on 4/21/2017.
 */
public class ProductServiceImpl extends AbstractCrudService<Product, Long> implements ProductService {

    private ProductDao productDao;
    private ConnectionManager cm;

    public ProductServiceImpl(ProductDao productDao, ConnectionManager cm) {
        this.productDao = productDao;
        this.cm = cm;
    }

    @Override
    public void create(Product product) {
        product.setVotesCount(0);
        product.setAvgRating(0d);
        Long id = productDao.create(product);
        product.setId(id);
    }

    @Override
    public void update(Product product) {
        Transaction.tx(cm, () -> {
            Product oldProduct = productDao.findById(product.getId());
            product.setVotesCount(oldProduct.getVotesCount());
            product.setAvgRating(oldProduct.getAvgRating());
            productDao.update(product);
        });
    }

    @Override
    public List<Product> findByDynamicFilter(ProductDynamicFilter filter) {
        return null;
    }

    @Override
    public List<Product> findByCategory(Long categoryId, int limit, int offset) {
        return productDao.findByCategory(categoryId, limit, offset);
    }

    @Override
    public int getProductsCount(Long categoryId) {
        return productDao.getProductsCount(categoryId);
    }

    @Override
    public List<Product> findEnabledByCategory(Long categoryId, int limit, int offset) {
        return productDao.findEnabledByCategory(categoryId, limit, offset);
    }

    @Override
    public Dao<Product, Long> getBackingDao() {
        return productDao;
    }
}
