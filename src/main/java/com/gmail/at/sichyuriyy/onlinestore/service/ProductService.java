package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDynamicFilter;

import java.util.List;

/**
 * Created by Yuriy on 4/7/2017.
 */
public interface ProductService extends CrudService<Product, Long> {

    List<Product> findByDynamicFilter(ProductDynamicFilter filter);
    List<Product> findByCategory(Long categoryId, int limit, int offset);
    int getProductsCount(Long categoryId);

    List<Product> findEnabledByCategory(Long categoryId, int limit, int offset);

}
