package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.TestData;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 4/3/2017.
 */
public class ProductDaoTest {

    private static final Double DELTA = 10e-7;

    private ProductDao productDao;
    private TestData testData;
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        productDao = new JdbcProductDao(connectionManager);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void findById() {
        Product expected = testData.getProduct(1L);
        Product actual = productDao.findById(1L);
        assertWeakEquals(expected, actual);
    }

    @Test
    public void create() {
        Product expected = new Product(null, "new product", "desc", testData.getCategory(1L),
                new BigDecimal(12), 3, 1, 4d, "https://img1", true);
        Long id = productDao.create(expected);
        expected.setId(id);

        Product actual = productDao.findById(id);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void update() {
        Product expected = testData.getProduct(1L);
        expected.setVotesCount(2);
        expected.setMainImageUrl("https://updated");
        expected.setPrice(new BigDecimal(777));
        expected.setTitle("updated_title");
        expected.setEnabled(false);
        expected.setAvgRating(2d);
        expected.setCategory(testData.getCategory(2L));
        expected.setCount(777);
        expected.setDescription("updated_description");

        productDao.update(expected);

        Product actual = productDao.findById(1L);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        assertNotNull(productDao.findById(4L));
        productDao.delete(4L);
        assertNull(productDao.findById(4L));
    }

    @Test
    public void findAll() throws Exception {
        List<Product> expected = Arrays.asList(
                testData.getProduct(1L),
                testData.getProduct(2L),
                testData.getProduct(3L),
                testData.getProduct(4L));
        List<Product> actual = productDao.findAll();

        assertWeakEquals(expected.get(0), actual.get(0));
        assertWeakEquals(expected.get(1), actual.get(1));
        assertWeakEquals(expected.get(2), actual.get(2));
        assertWeakEquals(expected.get(3), actual.get(3));
    }

    private void assertWeakEquals(Product expected, Product actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getCategory().getId(), actual.getCategory().getId());
        assertEquals(expected.getAvgRating(), actual.getAvgRating(), DELTA);
        assertEquals(expected.getCount(), actual.getCount());
        assertEquals(expected.getDescription(), actual.getDescription());
        assertEquals(expected.getEnabled(), actual.getEnabled());
        assertEquals(expected.getMainImageUrl(), actual.getMainImageUrl());
        assertEquals(0, expected.getPrice().compareTo(actual.getPrice()));
        assertEquals(expected.getTitle(), actual.getTitle());
        assertEquals(expected.getVotesCount(), actual.getVotesCount());
    }

    private URL getURL(String string) {
        try {
            return new URL(string);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
    }

}