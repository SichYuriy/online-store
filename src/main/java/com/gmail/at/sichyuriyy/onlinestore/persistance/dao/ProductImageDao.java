package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.ProductImage;

import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface ProductImageDao extends Dao<ProductImage, Long> {

    List<ProductImage> findByProduct(Long productId);
}
