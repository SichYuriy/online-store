package com.gmail.at.sichyuriyy.onlinestore.validation;

/**
 * Created by Yuriy on 4/12/2017.
 */
public interface EntityValidator<T> {
    boolean getValidationStatus(T entity);
}
