package com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yuriy on 4/28/2017.
 */
public interface ResponseResolver {

    void resolve(HttpServletRequest req, HttpServletResponse resp);
}
