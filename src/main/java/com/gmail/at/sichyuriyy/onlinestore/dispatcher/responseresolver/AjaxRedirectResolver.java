package com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yuriy on 4/28/2017.
 */
public class AjaxRedirectResolver implements ResponseResolver {

    private String redirectPath;

    public AjaxRedirectResolver(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    @Override
    public void resolve(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.setContentType("application/json");
            resp.getWriter().write("{\"redirect\":\"" + redirectPath + "\"}");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
