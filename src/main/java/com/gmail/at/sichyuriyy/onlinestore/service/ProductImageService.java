package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.domain.ProductImage;

import java.util.List;

/**
 * Created by Yuriy on 4/22/2017.
 */
public interface ProductImageService extends CrudService<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);
}
