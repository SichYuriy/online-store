package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.entity.ProductImage;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductImageDao;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.ProductImageService;

import java.util.List;

/**
 * Created by Yuriy on 4/22/2017.
 */
public class ProductImageServiceImpl extends AbstractCrudService<ProductImage, Long> implements ProductImageService {

    private ProductImageDao productImageDao;

    public ProductImageServiceImpl(ProductImageDao productImageDao) {
        this.productImageDao = productImageDao;
    }

    @Override
    public void create(ProductImage productImage) {
        Long id = productImageDao.create(productImage);
        productImage.setId(id);
    }

    @Override
    public List<ProductImage> findByProductId(Long productId) {
        return productImageDao.findByProduct(productId);
    }

    @Override
    public Dao<ProductImage, Long> getBackingDao() {
        return productImageDao;
    }
}
