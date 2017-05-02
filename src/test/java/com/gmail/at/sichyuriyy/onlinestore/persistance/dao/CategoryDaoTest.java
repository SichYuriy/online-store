package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.Category;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.TestData;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcCategoryDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 3/31/2017.
 */
public class CategoryDaoTest {

    private CategoryDao categoryDao;
    private TestData testData;
    private ConnectionManager connectionManager;


    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        categoryDao = new JdbcCategoryDao(connectionManager);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void findById() {
        Category expected = testData.getCategory(1L);
        Category actual = categoryDao.findById(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void create() {
        Category expected = new Category(null, "new_category");
        Long id = categoryDao.create(expected);
        expected.setId(id);

        Category actual = categoryDao.findById(id);

        assertEquals(expected, actual);
    }

    @Test
    public void update() {
        Category expected = testData.getCategory(1L);
        expected.setTitle("updated_title");

        categoryDao.update(expected);

        Category actual = categoryDao.findById(1L);

        assertEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        assertNotNull(categoryDao.findById(3L));
        categoryDao.delete(3L);
        assertNull(categoryDao.findById(3L));

    }

    @Test
    public void findAll() throws Exception {
        List<Category> expected = Arrays.asList(
                testData.getCategory(1L),
                testData.getCategory(2L),
                testData.getCategory(3L));
        List<Category> actual = categoryDao.findAll();

        assertEquals(expected, actual);
    }
}