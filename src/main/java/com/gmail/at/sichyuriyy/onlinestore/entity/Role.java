package com.gmail.at.sichyuriyy.onlinestore.entity;

import java.util.Collections;
import java.util.List;

/**
 * Created by Yuriy on 3/27/2017.
 */
public enum Role {
    CUSTOMER, ADMINISTRATOR;

    public static List<Role> adminRoles() {
        return Collections.singletonList(ADMINISTRATOR);
    }
}
