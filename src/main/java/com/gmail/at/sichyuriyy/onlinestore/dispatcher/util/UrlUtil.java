package com.gmail.at.sichyuriyy.onlinestore.dispatcher.util;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServlet;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.HttpMethod;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yuriy on 4/20/2017.
 */
public class UrlUtil {

    public static String getControllerUrl(String pathInfo) {
        pathInfo = pathInfo.replaceAll("[/]+","/");
        int pageSuffixIndex = pathInfo.lastIndexOf(DispatcherServlet.PAGE_SUFFIX);
        int endIndex = pageSuffixIndex  == -1 ? pathInfo.length() : pageSuffixIndex;
        return pathInfo.substring(0, endIndex);
    }

    public static HttpMethod getMethod(HttpServletRequest req) {
        String method = req.getParameter(DispatcherServlet.METHOD_PARAM);
        if (method == null) {
            method = req.getMethod().toUpperCase();
        }
        return HttpMethod.valueOf(method);
    }
}
