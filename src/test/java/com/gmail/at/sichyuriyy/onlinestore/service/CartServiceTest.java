package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.TestData;
import com.gmail.at.sichyuriyy.onlinestore.domain.CartItem;
import com.gmail.at.sichyuriyy.onlinestore.domain.ShoppingCart;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.CartItemDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcCartItemDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.CartServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 5/2/2017.
 */
public class CartServiceTest {


    private CartService cartService;
    private ConnectionManager connectionManager;
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
        cartService = new CartServiceImpl(cartItemDao, productDao);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void findByUser() {
        ShoppingCart actual = cartService.findByUser(1L);
        ShoppingCart expected = testData.getShoppingCart(1L);

        assertWeakEquals(actual, expected);
    }

    @Test
    public void addProductNotAvailable() {
        assertFalse(cartService.addProduct(1L, 1L));
        ShoppingCart actual = cartService.findByUser(1L);
        ShoppingCart expected = testData.getShoppingCart(1L);

        assertWeakEquals(actual, expected);
    }

    @Test
    public void addProduct() {
        assertTrue(cartService.addProduct(1L, 2L));
        ShoppingCart actual = cartService.findByUser(1L);
        ShoppingCart expected = testData.getShoppingCart(1L);
        expected.setCartItems(new ArrayList<>(expected.getCartItems()));
        expected.getCartItems().add(new CartItem(2L, testData.getProduct(2L), 1, testData.getUser(1L)));
        expected.setTotal(expected.getTotal().add(testData.getProduct(2L).getPrice()));
        assertWeakEquals(actual, expected);
    }

    @Test
    public void removeProduct() {
        cartService.removeProduct(1L, 1L);
        ShoppingCart actual = cartService.findByUser(1L);

        assertEquals(0, actual.getCartItems().size());
        assertEquals(0, actual.getTotal().compareTo(BigDecimal.ZERO));
    }

    @Test
    public void clearCart() {
        cartService.clearCart(1L);
        ShoppingCart actual = cartService.findByUser(1L);

        assertEquals(0, actual.getCartItems().size());
        assertEquals(0, actual.getTotal().compareTo(BigDecimal.ZERO));
    }

    private void assertWeakEquals(ShoppingCart expected, ShoppingCart actual) {
        if (expected.getCartItems() != null && actual.getCartItems() != null) {
            assertEquals(expected.getCartItems().size(), actual.getCartItems().size());
            Iterator<CartItem> expectedIt = expected.getCartItems().iterator();
            Iterator<CartItem> actualIt = actual.getCartItems().iterator();
            while(expectedIt.hasNext()) {
                CartItem expectedItem = expectedIt.next();
                CartItem actualItem = actualIt.next();
                assertWeakEquals(expectedItem, actualItem);
            }
        }
        assertEquals(0, expected.getTotal().compareTo(actual.getTotal()));
    }

    private void assertWeakEquals(CartItem expected, CartItem actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getProduct().getId(), actual.getProduct().getId());
        assertEquals(expected.getCount(), actual.getCount());
    }

}