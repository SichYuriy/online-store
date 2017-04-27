package com.gmail.at.sichyuriyy.onlinestore.dispatcher;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.ResponseResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Yuriy on 4/28/2017.
 */
public class ResponseService {

    private HttpServletRequest request;
    private HttpServletResponse response;

    private ResponseResolver responseResolver;

    public ResponseService(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    public ResponseResolver getResponseResolver() {
        return responseResolver;
    }

    public void setResponseResolver(ResponseResolver responseResolver) {
        this.responseResolver = responseResolver;
    }

    public void resolveResponse() {
        if (responseResolver != null) {
            responseResolver.resolve(request, response);
        }
    }
}
