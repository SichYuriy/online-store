package com.gmail.at.sichyuriyy.onlinestore.validation.validator;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.validation.EntityValidator;

/**
 * Created by Yuriy on 4/24/2017.
 */
public class ProductValidator implements EntityValidator<Product> {

    @Override
    public boolean getValidationStatus(Product product) {
        return product.getTitle() != null
                && product.getTitle().length() >= 3
                && product.getTitle().length() <= 30
                && product.getCategory() != null
                && product.getCategory().getId() != null
                && product.getEnabled() != null
                && product.getDescription() != null
                && product.getDescription().length() <= 255
                && product.getMainImageUrl() != null
                && product.getMainImageUrl().length() <= 255
                && product.getCount() != null
                && product.getCount() >= 0
                && product.getPrice() != null
                && product.getPrice().doubleValue() >= 0;
    }
}
