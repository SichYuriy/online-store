package com.gmail.at.sichyuriyy.onlinestore.security;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.HttpMethod;
import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;

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

        if(!checkPermissions(servletRequest, servletResponse, roles)) {
            return;
        }

        filterChain.doFilter(httpRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }

    private User getCurrentUser(HttpServletRequest req) {
        return (User) req.getSession(true).getAttribute("user");
    }


    private boolean checkPermissions(ServletRequest request, ServletResponse response, List<Role> roles)
            throws ServletException, IOException {
        String extraPath = ((HttpServletRequest) request).getRequestURI();

        HttpMethod method = HttpMethod.valueOf(((HttpServletRequest) request).getMethod());

        if(!SecurityContext.INSTANCE.allowed(extraPath, method, roles)) {
            if (!roles.isEmpty()) {
                ((HttpServletResponse) response).
                        sendError(HttpServletResponse.SC_FORBIDDEN);
                throw new AccessDeniedException();
            }
            ((HttpServletRequest) request).getSession().setAttribute("forbidden_page", extraPath);
            request.getRequestDispatcher(SecurityContext.INSTANCE.getLoginPage())
                    .forward(request, response);

            return false;
        }

        return true;
    }
}
