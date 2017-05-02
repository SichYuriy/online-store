package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.TestData;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.Review;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcReviewDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc.JdbcUserDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.service.impl.ReviewServiceImpl;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.Timestamp;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 5/2/2017.
 */
public class ReviewServiceTest {

    private static final double DELTA = 10e-7;

    private ConnectionManager connectionManager;
    private ReviewService reviewService;
    private TestData testData;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        ServiceLocator.INSTANCE.add(TransactionManager.class, new TransactionManager(connectionManager));
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("database.sql"));
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_data.sql"));
        reviewService = new ReviewServiceImpl(
                new JdbcReviewDao(connectionManager),
                new JdbcUserDao(connectionManager),
                new JdbcProductDao(connectionManager)
        );
        testData = new TestData();
    }

    @After
    public void tearDown() throws Exception {
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("clear_database.sql"));
    }

    @Test
    public void testConsistency() throws Exception {
        Review review1 = new Review(null, testData.getUser(1L), testData.getProduct(4L),
                4.0d, "desc1", new Timestamp(0));
        Review review2 = new Review(null, testData.getUser(3L), testData.getProduct(4L),
                3.0d, "desc2", new Timestamp(1));

        reviewService.create(review1);
        reviewService.create(review2);

        Product product = new JdbcProductDao(connectionManager).findById(4L);

        assertEquals(new Integer(2), product.getVotesCount());
        assertEquals(3.5d, product.getAvgRating(), DELTA);
    }

    @Test
    public void update() {
        Review review = testData.getReview(3L);
        review.setRating(5d);

        reviewService.update(review);

        Product product = new JdbcProductDao(connectionManager).findById(2L);

        assertEquals(new Integer(2), product.getVotesCount());
        assertEquals(5d, product.getAvgRating(), DELTA);
    }

    @Test
    public void delete() {
        reviewService.delete(3L);

        Product product = new JdbcProductDao(connectionManager).findById(2L);

        assertEquals(new Integer(1), product.getVotesCount());
        assertEquals(5d, product.getAvgRating(), DELTA);
    }

    @Test
    public void canVoteTrue() throws Exception {
        assertTrue(reviewService.canVote(1L));
    }

    @Test
    public void canVoteFalse() throws Exception {
        assertFalse(reviewService.canVote(2L));
    }
}