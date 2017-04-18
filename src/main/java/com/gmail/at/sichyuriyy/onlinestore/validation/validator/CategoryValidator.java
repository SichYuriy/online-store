package com.gmail.at.sichyuriyy.onlinestore.validation.validator;

import com.gmail.at.sichyuriyy.onlinestore.entity.Category;
import com.gmail.at.sichyuriyy.onlinestore.validation.EntityValidator;

/**
 * Created by Yuriy on 4/12/2017.
 */
public class CategoryValidator implements EntityValidator<Category> {
    @Override
    public boolean getValidationStatus(Category entity) {
        return entity.getTitle() != null
                && entity.getTitle().length() > 3
                && entity.getTitle().length() < 26;
    }
}
