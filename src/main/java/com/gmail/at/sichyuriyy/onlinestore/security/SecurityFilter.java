package com.gmail.at.sichyuriyy.onlinestore.security;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServlet;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.HttpMethod;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.util.UrlUtil;
import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


/**
 * Created by Yuriy on 4/6/2017.
 */
@WebFilter(urlPatterns = "/*")
public class SecurityFilter implements Filter {

    private static final Logger LOGGER = LogManager.getLogger(SecurityFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest =
                new SecuredServletRequest((HttpServletRequest) servletRequest);

        User currentUser = getCurrentUser(httpRequest);

        List<Role> roles = Objects.isNull(currentUser) ?
                new ArrayList<>() : currentUser.getRoles();

        if(checkPermissions(servletRequest, servletResponse, roles)) {
            filterChain.doFilter(httpRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }

    private User getCurrentUser(HttpServletRequest req) {
        return (User) req.getSession(true).getAttribute("user");
    }


    private boolean checkPermissions(ServletRequest request, ServletResponse response, List<Role> roles)
            throws ServletException, IOException {
        String requestURI = ((HttpServletRequest) request).getRequestURI();
        String controllerUrl = UrlUtil.getControllerUrl(requestURI);
        String queryString = ((HttpServletRequest) request).getQueryString();
        String destination = requestURI;
        if (queryString != null) {
            destination += "?" + queryString;
        }
        HttpMethod method = UrlUtil.getMethod((HttpServletRequest)request);


        if(!SecurityContext.INSTANCE.allowed(controllerUrl, method, roles)) {
            if (!roles.isEmpty()) {
                ((HttpServletResponse) response).
                        sendError(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
            ((HttpServletRequest) request).getSession().setAttribute(DispatcherServlet.DESTINATION_KEY, destination);
            request.getRequestDispatcher(SecurityContext.INSTANCE.getLoginPage())
                    .forward(request, response);
            return false;
        }

        return true;
    }
}
