package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.factory;

import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.*;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.*;

/**
 * Created by Yuriy on 4/9/2017.
 */
public class JdbcDaoFactory implements DaoFactory {

    private ConnectionManager connectionManager;

    public JdbcDaoFactory(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    @Override
    public CategoryDao getCategoryDao() {
        return new JdbcCategoryDao(connectionManager);
    }

    @Override
    public LineItemDao getLineItemDao() {
        return new JdbcLineItemDao(connectionManager);
    }

    @Override
    public OrderDao getOrderDao() {
        return new JdbcOrderDao(connectionManager);
    }

    @Override
    public ProductDao getProductDao() {
        return new JdbcProductDao(connectionManager);
    }

    @Override
    public ProductImageDao getProductImage() {
        return new JdbcProductImageDao(connectionManager);
    }

    @Override
    public ReviewDao getRewReviewDao() {
        return new JdbcReviewDao(connectionManager);
    }

    @Override
    public UserDao getUserDao() {
        return new JdbcUserDao(connectionManager);
    }
}
