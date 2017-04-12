package com.gmail.at.sichyuriyy.onlinestore.validation;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;

/**
 * Created by Yuriy on 4/12/2017.
 */
public interface RequestMapper<T> {
    T map(RequestService requestService);
 }
