package com.gmail.at.sichyuriyy.onlinestore.validation.validator;

import com.gmail.at.sichyuriyy.onlinestore.domain.ProductImage;
import com.gmail.at.sichyuriyy.onlinestore.validation.EntityValidator;

/**
 * Created by Yuriy on 5/1/2017.
 */
public class ProductImageValidator implements EntityValidator<ProductImage> {

    @Override
    public boolean getValidationStatus(ProductImage image) {
        return image.getProduct() != null
                && image.getProduct().getId() != null
                && image.getImageUrl() != null
                && image.getSmallVersionUrl() != null;
    }
}
