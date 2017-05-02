package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.TestData;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.ShoppingCart;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.*;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.*;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.CartServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.OrderServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 5/2/2017.
 */
public class OrderServiceTest {

    private ConnectionManager connectionManager;
    private CartService cartService;
    private OrderService orderService;
    private TestData testData;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ServiceLocator.INSTANCE.add(TransactionManager.class, new TransactionManager(connectionManager));
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        CartItemDao cartItemDao = new JdbcCartItemDao(connectionManager);
        ProductDao productDao = new JdbcProductDao(connectionManager);
        OrderDao orderDao = new JdbcOrderDao(connectionManager);
        UserDao userDao = new JdbcUserDao(connectionManager);
        LineItemDao lineItemDao = new JdbcLineItemDao(connectionManager);
        cartService = new CartServiceImpl(cartItemDao, productDao);
        orderService = new OrderServiceImpl(orderDao, userDao, lineItemDao, productDao, cartService);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void makeOrder() {
        assertTrue(orderService.makeOrder(1L));

        ShoppingCart shoppingCart = cartService.findByUser(1L);
        Product product = new JdbcProductDao(connectionManager).findById(1L);


        assertEquals(new Integer(0), product.getCount());
        assertTrue(shoppingCart.getCartItems().isEmpty());

    }

}