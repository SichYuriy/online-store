package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.Product;

import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface ProductDao extends Dao<Product, Long> {

    List<Product> findByDynamicFilter(ProductDynamicFilter filter);
    List<Product> findByCategory(Long categoryId, int limit, int offset);
    int getProductsCount(Long categoryId);

    List<Product> findEnabledByCategory(Long categoryId, int limit, int offset);

    Integer getEnabledProductsCount(Long categoryId);
}
