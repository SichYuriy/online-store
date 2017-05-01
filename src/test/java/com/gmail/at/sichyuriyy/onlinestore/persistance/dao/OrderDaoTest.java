package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.Order;
import com.gmail.at.sichyuriyy.onlinestore.domain.OrderStatus;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.TestData;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcOrderDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;


/**
 * Created by Yuriy on 4/3/2017.
 */
public class OrderDaoTest {

    private OrderDao orderDao;
    private TestData testData;
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        orderDao = new JdbcOrderDao(connectionManager);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void findByUser() {
        List<Order> actual = orderDao.findByUser(1L);
        assertWeakEquals(testData.getOrder(2L), actual.get(0));
        assertWeakEquals(testData.getOrder(1L), actual.get(1));
    }

    @Test
    public void findPart() {
        List<Order> actual = orderDao.findPart(2, 1);
        assertWeakEquals(testData.getOrder(2L), actual.get(0));
        assertWeakEquals(testData.getOrder(3L), actual.get(1));
    }

    @Test
    public void findByStatusPart() {
        List<Order> actual = orderDao.findByStatusPart(OrderStatus.OVERDUE, 1, 0);
        assertWeakEquals(testData.getOrder(3L), actual.get(0));
    }

    @Test
    public void findById() {
        Order expected = testData.getOrder(1L);
        Order actual = orderDao.findById(1L);
        assertWeakEquals(expected, actual);
    }

    @Test
    public void create() {
        Order expected = new Order(null, new Timestamp(777), OrderStatus.CREATED, testData.getUser(1L));
        Long id = orderDao.create(expected);
        expected.setId(id);

        Order actual = orderDao.findById(id);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void update() {
        Order expected = testData.getOrder(1L);
        expected.setUser(testData.getUser(2L));
        expected.setStatus(OrderStatus.CANCELED);
        expected.setDate(new Timestamp(0));

        orderDao.update(expected);

        Order actual = orderDao.findById(1L);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        assertNotNull(orderDao.findById(4L));
        orderDao.delete(4L);
        assertNull(orderDao.findById(4L));
    }

    @Test
    public void findAll() throws Exception {
        List<Order> expected = Arrays.asList(
                testData.getOrder(4L),
                testData.getOrder(2L),
                testData.getOrder(3L),
                testData.getOrder(1L));
        List<Order> actual = orderDao.findAll();

        assertWeakEquals(expected.get(0), actual.get(0));
        assertWeakEquals(expected.get(1), actual.get(1));
        assertWeakEquals(expected.get(2), actual.get(2));
        assertWeakEquals(expected.get(3), actual.get(3));
    }

    private void assertWeakEquals(Order expected, Order actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getUser().getId(), actual.getUser().getId());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getStatus(), actual.getStatus());
    }

}