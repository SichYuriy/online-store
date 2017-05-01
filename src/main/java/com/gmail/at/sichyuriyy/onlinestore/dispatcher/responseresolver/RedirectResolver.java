package com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Yuriy on 4/28/2017.
 */
public class RedirectResolver implements ResponseResolver {

    private static final Logger LOGGER = LogManager.getLogger(RedirectResolver.class);

    private String redirectPath;

    public RedirectResolver(String redirectPath) {
        this.redirectPath = redirectPath;
    }

    @Override
    public void resolve(HttpServletRequest req, HttpServletResponse resp) {
        try {
            resp.sendRedirect(redirectPath);
        } catch (IOException e) {
            LOGGER.warn("An exception happened at redirecting");
        }
    }
}
