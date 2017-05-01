package com.gmail.at.sichyuriyy.onlinestore.application;

import com.gmail.at.sichyuriyy.onlinestore.controller.*;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServlet;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.DispatcherServletBuilder;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.HttpMethod;
import com.gmail.at.sichyuriyy.onlinestore.domain.Role;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.factory.DaoFactory;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.factory.JdbcDaoFactory;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.security.SecurityContext;
import com.gmail.at.sichyuriyy.onlinestore.service.*;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.*;
import com.gmail.at.sichyuriyy.onlinestore.util.PropertiesLoader;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import javax.servlet.ServletContext;
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
                .addMapping("/register", new RegisterController())
                .withSecurity("/admin/categories", new CategoriesController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/admin/newCategory", new AdminNewCategoryController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/admin/editCategory", new AdminEditCategoryController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .addMapping("/product", new ProductController())
                .withSecurity("/user/reviews", new ReviewsController())
                    .httpMethods(HttpMethod.all()).roles(Role.all()).endConstraints()
                .withSecurity("/user/newReview", new NewReviewController())
                    .httpMethods(HttpMethod.all()).roles(Role.all()).endConstraints()
                .withSecurity("/user/editReview", new EditReviewController())
                    .httpMethods(HttpMethod.all()).roles(Role.all()).endConstraints()
                .withSecurity("/admin/users", new AdminUsersController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/admin/newAdmin", new NewAdminController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/admin/orders", new AdminOrdersController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/order", new OrderViewController())
                    .httpMethods(HttpMethod.all()).roles(Role.all()).endConstraints()
                .withSecurity("/user/orders", new UserOrdersController())
                    .httpMethods(HttpMethod.all()).roles(Role.all()).endConstraints()
                .withSecurity("/products", new ProductsController())
                    .httpMethods(HttpMethod.modifying()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/admin/newProduct", new AdminNewProductController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/admin/editProduct", new AdminEditProductController())
                    .httpMethods(HttpMethod.all()).roles(Role.adminRoles()).endConstraints()
                .withSecurity("/user/shoppingCart", new ShoppingCartController())
                    .httpMethods(HttpMethod.all()).roles(Role.all()).endConstraints()
                .withSecurity("/user/cartItems", new CartItemsController())
                    .httpMethods(HttpMethod.all()).roles(Role.all()).endConstraints()
                .buildAndRegister("Command Dispatcher Servlet", "/app/*", servletContext);
    }

    private void readProperties() {
        appProperties = PropertiesLoader.INSTANCE.getAppProperties();
    }

    private void preparePersistence() {
        connectionManager = ConnectionManager.fromJndi(appProperties.getProperty(AppProperties.CP_JNDI));

        serviceLocator.add(ConnectionManager.class, connectionManager);
        daoFactory = new JdbcDaoFactory(connectionManager);
        TransactionManager transactionManager = new TransactionManager(connectionManager);
        serviceLocator.add(TransactionManager.class, transactionManager);
    }

    private void prepareServices() {
        UserService userService =
                new UserServiceImpl(daoFactory.getUserDao());
        CategoryService categoryService =
                new CategoryServiceImpl(daoFactory.getCategoryDao());
        AuthService authService =
                new AuthServiceImpl(userService);
        ProductService productService =
                new ProductServiceImpl(daoFactory.getProductDao());
        ReviewService reviewService =
                new ReviewServiceImpl(daoFactory.getReviewDao(),
                        daoFactory.getUserDao(), daoFactory.getProductDao());
        ProductImageService productImageService =
                new ProductImageServiceImpl(daoFactory.getProductImageDao());
        OrderService orderService =
                new OrderServiceImpl(daoFactory.getOrderDao(), daoFactory.getUserDao(),
                        daoFactory.getLineItemDao(), daoFactory.getProductDao());
        CartService cartService =
                new CartServiceImpl(daoFactory.getCartItemDao(), daoFactory.getProductDao());

        //TODO: add all services

        serviceLocator.add(UserService.class, userService);
        serviceLocator.add(AuthService.class, authService);
        serviceLocator.add(CategoryService.class, categoryService);
        serviceLocator.add(ProductService.class, productService);
        serviceLocator.add(ReviewService.class, reviewService);
        serviceLocator.add(ProductImageService.class, productImageService);
        serviceLocator.add(OrderService.class, orderService);
        serviceLocator.add(CartService.class, cartService);

    }

}
