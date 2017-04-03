package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.entity.ProductImage;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.TestData;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcProductImageDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 4/3/2017.
 */
public class ProductImageDaoTest {

    private ProductImageDao productImageDao;
    private TestData testData;
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        productImageDao = new JdbcProductImageDao(connectionManager);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void findByProduct() {
        List<ProductImage> actual = productImageDao.findByProduct(1L);

        assertWeakEquals(testData.getProductImage(1L), actual.get(0));
        assertWeakEquals(testData.getProductImage(2L), actual.get(1));
    }

    @Test
    public void findById() {
        ProductImage expected = testData.getProductImage(1L);
        ProductImage actual = productImageDao.findById(1L);
        assertWeakEquals(expected, actual);
    }

    @Test
    public void create() {
        ProductImage expected = new ProductImage(null, testData.getProduct(1L),
                "https://img777", "https://smallImage777");
        Long id = productImageDao.create(expected);
        expected.setId(id);

        ProductImage actual = productImageDao.findById(id);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void update() {
        ProductImage expected = testData.getProductImage(1L);
        expected.setImageUrl("https://updated");
        expected.setSmallVersionUrl("https://updated_sm");
        expected.setProduct(testData.getProduct(2L));

        productImageDao.update(expected);

        ProductImage actual = productImageDao.findById(1L);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        assertNotNull(productImageDao.findById(1L));
        productImageDao.delete(1L);
        assertNull(productImageDao.findById(1L));
    }

    @Test
    public void findAll() throws Exception {
        List<ProductImage> expected = Arrays.asList(
                testData.getProductImage(1L),
                testData.getProductImage(2L),
                testData.getProductImage(3L));
        List<ProductImage> actual = productImageDao.findAll();

        assertWeakEquals(expected.get(0), actual.get(0));
        assertWeakEquals(expected.get(1), actual.get(1));
        assertWeakEquals(expected.get(2), actual.get(2));
    }

    private void assertWeakEquals(ProductImage expected, ProductImage actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getProduct().getId(), actual.getProduct().getId());
        assertEquals(expected.getImageUrl(), actual.getImageUrl());
        assertEquals(expected.getSmallVersionUrl(), actual.getSmallVersionUrl());
    }


}