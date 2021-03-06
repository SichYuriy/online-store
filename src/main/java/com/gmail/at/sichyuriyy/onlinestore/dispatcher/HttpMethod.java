package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Yuriy on 4/4/2017.
 */
public enum HttpMethod {
    GET, PUT, POST, DELETE;

    public static List<HttpMethod> all() {
        return Arrays.asList(HttpMethod.values());
    }

    public static List<HttpMethod> modifying() {
        return Arrays.asList(PUT, POST, DELETE);
    }
}
