package com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yuriy on 5/1/2017.
 */
public class AjaxErrorResolver implements ResponseResolver {

    private String errorMessage;

    public AjaxErrorResolver(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public void resolve(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"error\":\"" + errorMessage + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
