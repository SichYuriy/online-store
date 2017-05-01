package com.gmail.at.sichyuriyy.onlinestore.security;

import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

/**
 * Created by Yuriy on 4/6/2017.
 */
public class SecuredServletRequest extends HttpServletRequestWrapper {

    private SecurityContext securityContext = SecurityContext.INSTANCE;


    public SecuredServletRequest(HttpServletRequest request) {
        super(request);
    }


    @Override
    public boolean isUserInRole(String role) {
        User user = getCurrentUser();
        if (user == null) {
            return false;
        }

        return user.getRoles().stream().map(Enum::name).anyMatch(s -> s.equals(role));
    }

    @Override
    public Principal getUserPrincipal() {
        User user = getCurrentUser();
        if (user == null) {
            return null;
        }
        return () -> user.getId().toString();
    }

    @Override
    public void login(String username, String password) throws ServletException {
        UserDao userDao = SecurityContext.INSTANCE.getUserDao();
        User user = userDao.findByLoginPassword(username, password);
        if (user != null) {
            getSession().setAttribute("user", user);
            getSession().setAttribute("loggedIn", true);
        } else {
            getSession().invalidate();
            throw new ServletException("bad login or password");
        }
    }

    @Override
    public void logout() throws ServletException {
        getSession().invalidate();
    }

    private User getCurrentUser() {
        return (User) getSession(true).getAttribute("user");
    }
}
