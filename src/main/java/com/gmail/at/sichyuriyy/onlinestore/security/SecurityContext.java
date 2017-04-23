package com.gmail.at.sichyuriyy.onlinestore.security;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.HttpMethod;
import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.UserDao;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuriy on 4/4/2017.
 */
public enum SecurityContext {
    INSTANCE;

    public static final String SESSION_USER = "user";

    private static final String LOGIN_PAGE = "/pages/login.jsp";

    private UserDao userDao = ServiceLocator.INSTANCE.get(UserDao.class);

    private List<SecurityConstraint> constraints = new ArrayList<>();

    public boolean allowed(String path, HttpMethod method, List<Role> roles) {
        for (SecurityConstraint constraint: constraints) {
            if (constraint.matches(path, method) && !constraint.allowed(roles)) {
                return false;
            }
        }
        return true;
    }

    public UserDao getUserDao() {
        return userDao;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public String getLoginPage() {
        return LOGIN_PAGE;
    }

    public void addConstraint(String path, List<HttpMethod> methods, List<Role> roles) {
        constraints.add(new SecurityConstraint(path, methods, roles));
    }

    public User getCurrentUser(HttpServletRequest req) {
        return (User) req.getSession(true).getAttribute(SESSION_USER);
    }

    public static class SecurityConstraint {

        private String path;
        private List<HttpMethod> methods;
        private List<Role> roles;

        public SecurityConstraint(String path, List<HttpMethod> methods, List<Role> roles) {
            this.path = path;
            this.methods = methods;
            this.roles = roles;
        }

        public boolean matches(String path, HttpMethod method) {
            return this.path.equals(path) && methods.contains(method);
        }

        public boolean allowed(List<Role> roles) {
            for (Role role: roles) {
                if (this.roles.contains(role)) {
                    return true;
                }
            }
            return false;
        }
    }

}
