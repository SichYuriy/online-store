package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.entity.LineItem;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.TestData;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcLineItemDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 4/2/2017.
 */
public class LineItemDaoTest {

    private LineItemDao lineItemDao;
    private TestData testData;
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        lineItemDao = new JdbcLineItemDao(connectionManager);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void findByOrder() throws Exception {
        List<LineItem> actual = lineItemDao.findByOrder(1L);

        assertWeakEquals(testData.getLineItem(1L), actual.get(0));
        assertWeakEquals(testData.getLineItem(2L), actual.get(1));
    }

    @Test
    public void findById() {
        LineItem expected = testData.getLineItem(1L);
        LineItem actual = lineItemDao.findById(1L);
        assertWeakEquals(expected, actual);
    }

    @Test
    public void create() {
        LineItem expected = new LineItem(null, testData.getOrder(1L), testData.getProduct(1L),
                new BigDecimal(777), 2);
        Long id = lineItemDao.create(expected);
        expected.setId(id);

        LineItem actual = lineItemDao.findById(id);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void update() {
        LineItem expected = testData.getLineItem(1L);
        expected.setCount(777);
        expected.setOrder(testData.getOrder(2L));
        expected.setProduct(testData.getProduct(2L));
        expected.setTempPrice(new BigDecimal(777));

        lineItemDao.update(expected);

        LineItem actual = lineItemDao.findById(1L);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        assertNotNull(lineItemDao.findById(1L));
        lineItemDao.delete(1L);
        assertNull(lineItemDao.findById(1L));
    }

    @Test
    public void findAll() throws Exception {
        List<LineItem> expected = Arrays.asList(
                testData.getLineItem(1L),
                testData.getLineItem(2L),
                testData.getLineItem(3L),
                testData.getLineItem(4L));
        List<LineItem> actual = lineItemDao.findAll();

        assertWeakEquals(expected.get(0), actual.get(0));
        assertWeakEquals(expected.get(1), actual.get(1));
        assertWeakEquals(expected.get(2), actual.get(2));
        assertWeakEquals(expected.get(3), actual.get(3));
    }

    private void assertWeakEquals(LineItem expected, LineItem actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getOrder().getId(), actual.getOrder().getId());
        assertEquals(expected.getProduct().getId(), actual.getProduct().getId());
        assertEquals(expected.getCount(), actual.getCount());
        assertEquals(0, expected.getTempPrice().compareTo(actual.getTempPrice()));
    }

}