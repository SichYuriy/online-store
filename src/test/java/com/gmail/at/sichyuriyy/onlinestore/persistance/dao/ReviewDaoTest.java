package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.Review;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.TestData;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcReviewDao;
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
public class ReviewDaoTest {

    private ReviewDao reviewDao;
    private TestData testData;
    private ConnectionManager connectionManager;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        reviewDao = new JdbcReviewDao(connectionManager);
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void findByProduct() {
        List<Review> actual = reviewDao.findByProduct(2L);

        assertWeakEquals(testData.getReview(3L), actual.get(0));
        assertWeakEquals(testData.getReview(2L), actual.get(1));
    }

    @Test
    public void findById() {
        Review expected = testData.getReview(1L);
        Review actual = reviewDao.findById(1L);
        assertWeakEquals(expected, actual);
    }

    @Test
    public void create() {
        Review expected = new Review(null, testData.getUser(1L), testData.getProduct(1L),
                4d, "desc", new Timestamp(777));
        Long id = reviewDao.create(expected);
        expected.setId(id);

        Review actual = reviewDao.findById(id);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void update() {
        Review expected = testData.getReview(1L);
        expected.setDate(new Timestamp(777));
        expected.setProduct(testData.getProduct(2L));
        expected.setAuthor(testData.getUser(2L));
        expected.setRating(5d);
        expected.setDescription("updated_desc");

        reviewDao.update(expected);

        Review actual = reviewDao.findById(1L);

        assertWeakEquals(expected, actual);
    }

    @Test
    public void remove() throws Exception {
        assertNotNull(reviewDao.findById(1L));
        reviewDao.delete(1L);
        assertNull(reviewDao.findById(1L));
    }

    @Test
    public void findAll() throws Exception {
        List<Review> expected = Arrays.asList(
                testData.getReview(4L),
                testData.getReview(3L),
                testData.getReview(2L),
                testData.getReview(1L));
        List<Review> actual = reviewDao.findAll();

        assertWeakEquals(expected.get(0), actual.get(0));
        assertWeakEquals(expected.get(1), actual.get(1));
        assertWeakEquals(expected.get(2), actual.get(2));
        assertWeakEquals(expected.get(3), actual.get(3));
    }

    private void assertWeakEquals(Review expected, Review actual) {
        assertEquals(expected.getId(), actual.getId());
        assertEquals(expected.getProduct().getId(), actual.getProduct().getId());
        assertEquals(expected.getAuthor().getId(), actual.getAuthor().getId());
        assertEquals(expected.getDate(), actual.getDate());
        assertEquals(expected.getRating(), actual.getRating());
        assertEquals(expected.getDescription(), actual.getDescription());
    }

}