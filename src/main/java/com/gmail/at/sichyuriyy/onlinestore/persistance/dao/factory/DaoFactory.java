package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.factory;

import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.*;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface DaoFactory {
    CategoryDao getCategoryDao();
    LineItemDao getLineItemDao();
    OrderDao getOrderDao();
    ProductDao getProductDao();
    ProductImageDao getProductImage();
    ReviewDao getRewReviewDao();
    UserDao getUserDao();
}
