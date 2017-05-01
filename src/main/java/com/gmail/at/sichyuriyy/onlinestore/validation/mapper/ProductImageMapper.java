package com.gmail.at.sichyuriyy.onlinestore.validation.mapper;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.ProductImage;
import com.gmail.at.sichyuriyy.onlinestore.validation.RequestMapper;

/**
 * Created by Yuriy on 5/1/2017.
 */
public class ProductImageMapper implements RequestMapper<ProductImage> {


    @Override
    public ProductImage map(RequestService requestService) {
        ProductImage productImage = new ProductImage();
        productImage.setId(requestService.getLong("id"));
        productImage.setImageUrl(requestService.getString("imageUrl"));
        productImage.setSmallVersionUrl(requestService.getString("imageUrl"));
        productImage.setProduct(new Product(requestService.getLong("productId")));
        return productImage;
    }
}
