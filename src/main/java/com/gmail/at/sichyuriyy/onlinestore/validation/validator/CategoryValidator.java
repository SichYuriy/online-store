package com.gmail.at.sichyuriyy.onlinestore.validation.validator;

import com.gmail.at.sichyuriyy.onlinestore.domain.Category;
import com.gmail.at.sichyuriyy.onlinestore.validation.EntityValidator;

/**
 * Created by Yuriy on 4/12/2017.
 */
public class CategoryValidator implements EntityValidator<Category> {

    private static final String TITLE_REGEX = "^[\\wа-яА-ЯіІїЇ@#$%^&+\\-= ]{1,64}$";

    @Override
    public boolean getValidationStatus(Category entity) {
        return entity.getTitle() != null
                && entity.getTitle().length() >= 4
                && entity.getTitle().length() <= 25
                && entity.getTitle().matches(TITLE_REGEX);
    }
}
