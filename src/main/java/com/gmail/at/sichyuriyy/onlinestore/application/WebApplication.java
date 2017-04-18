package com.gmail.at.sichyuriyy.onlinestore.application;

import com.gmail.at.sichyuriyy.onlinestore.controller.*;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServlet;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServletBuilder;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.HttpMethod;
import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.factory.DaoFactory;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.factory.JdbcDaoFactory;
import com.gmail.at.sichyuriyy.onlinestore.security.SecurityContext;
import com.gmail.at.sichyuriyy.onlinestore.service.AuthService;
import com.gmail.at.sichyuriyy.onlinestore.service.CategoryService;
import com.gmail.at.sichyuriyy.onlinestore.service.UserService;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.AuthServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.CategoryServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.UserServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.util.PropertiesLoader;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import javax.servlet.ServletContext;
import java.util.Arrays;
import java.util.Collections;
import java.util.Properties;

/**
 * Created by Yuriy on 4/7/2017.
 */
public class WebApplication {

    private ServletContext servletContext;

    private ServiceLocator serviceLocator;

    private ConnectionManager connectionManager;

    private DaoFactory daoFactory;

    private Properties appProperties;

    public void init() {

        serviceLocator = ServiceLocator.INSTANCE;

        readProperties();
        preparePersistence();
        prepareServices();
        buildDispatcherServlet();

        SecurityContext.INSTANCE.setUserDao(daoFactory.getUserDao());
    }

    public void setServletContext(ServletContext servletContext) {
        this.servletContext = servletContext;
    }


    private DispatcherServlet buildDispatcherServlet() {
        DispatcherServletBuilder builder = new DispatcherServletBuilder();

        return builder.addMapping("/", new RedirectController("/index"))
                .addMapping("/index", new HomeController())
                .addMapping("/login", new LoginController())
                .addMapping("/logout", new LogoutController())
                .withSecurity("/admin/categories", new CategoriesController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles())
                    .endConstraints()
                .withSecurity("/admin/newCategory", new AdminNewCategoryController())
                    .httpMethods(HttpMethod.all())
                    .roles(Role.adminRoles())
                    .endConstraints()
                .buildAndRegister("Command Dispatcher Servlet", "/app/*", servletContext);
    }

    private void readProperties() {
        appProperties = PropertiesLoader.INSTANCE.getAppProperties();
    }

    private void preparePersistence() {
        ConnectionManager connectionManager = ConnectionManager.fromJndi(appProperties.getProperty(AppProperties.CP_JNDI));
        serviceLocator.add(ConnectionManager.class, connectionManager);
        daoFactory = new JdbcDaoFactory(connectionManager);
    }

    private void prepareServices() {
        UserService userService =
                new UserServiceImpl(daoFactory.getUserDao());
        CategoryService categoryService =
                new CategoryServiceImpl(daoFactory.getCategoryDao());
        AuthService authService =
                new AuthServiceImpl(userService);

        //TODO: add all services

        serviceLocator.add(UserService.class, userService);
        serviceLocator.add(AuthService.class, authService);
        serviceLocator.add(CategoryService.class, categoryService);

    }

}
