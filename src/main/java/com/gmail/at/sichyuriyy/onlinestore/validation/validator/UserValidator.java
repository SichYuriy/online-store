package com.gmail.at.sichyuriyy.onlinestore.validation.validator;

import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.validation.EntityValidator;

/**
 * Created by Yuriy on 4/21/2017.
 */
public class UserValidator implements EntityValidator<User> {

    @Override
    public boolean getValidationStatus(User user) {
        return user.getBlackList() != null
                && user.getLogin() != null
                && user.getLogin().length() >= 5
                && user.getLogin().length() <= 25
                && user.getPassword() != null
                && user.getPassword().length() >= 6
                && user.getPassword().length() <= 25;
    }
}
