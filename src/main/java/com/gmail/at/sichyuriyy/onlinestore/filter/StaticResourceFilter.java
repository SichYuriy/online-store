package com.gmail.at.sichyuriyy.onlinestore.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuriy on 4/7/2017.
 */
public class StaticResourceFilter implements Filter {
    private String resourcePath = "/resources/";

    private String pagePath = "/pages/";

    private String forwardPath = "/app";

    public StaticResourceFilter() {
    }

    public StaticResourceFilter(String resourcePath, String pagePath, String forwardPath) {
        this.resourcePath = resourcePath;
        this.pagePath = pagePath;
        this.forwardPath = forwardPath;
    }

    public StaticResourceFilter(String forwardPath) {
        this.forwardPath = forwardPath;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        String path = req.getRequestURI().substring(req.getContextPath().length());

        if (path.startsWith(resourcePath) || path.startsWith(pagePath)
                || path.startsWith(forwardPath)) {
            chain.doFilter(request, response); // Goes to default servlet.
        } else {
            req.getRequestDispatcher(forwardPath + path).forward(request, response); // Goes to controller.
        }
    }

    @Override
    public void destroy() {
    }
}
