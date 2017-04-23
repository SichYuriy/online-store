package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.entity.Review;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ReviewDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.ReviewMapper;

import java.util.List;

/**
 * Created by Yuriy on 3/30/2017.
 */
public class JdbcReviewDao implements ReviewDao {

    private static final String INSERT_REVIEW = "INSERT INTO `review`" +
            "(`author_id`, `product_id`, `rating`, `description`, `date`) VALUES(?, ?, ?, ?, ?)";

    private static final String SELECT_ALL_REVIEWS = "SELECT * FROM `review` ORDER BY `date` DESC";

    private static final String SELECT_REVIEW_BY_PRODUCT_ID = "SELECT * FROM `review` WHERE `product_id`=? " +
            "ORDER BY `date` DESC";

    private static final String SELECT_REVIEW_BY_ID = "SELECT * FROM `review` WHERE `id`=?";

    private static final String UPDATE_REVIEW = "UPDATE `review` " +
            "SET `author_id`=?, `product_id`=?, `rating`=?, `description`=?, `date`=? WHERE `id`=?";

    private static final String DELETE_REVIEW = "DELETE FROM `review` WHERE `id`=?";

    private static final String SELECT_REVIEW_BY_USER_PRODUCT_ID = "SELECT * FROM `review` WHERE `author_id`=? " +
            "AND product_id=?";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcReviewDao(ConnectionManager cm) {
        this.cm = cm;
        jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public Long create(Review review) {
        return jdbcTemplate.insert(INSERT_REVIEW,
                review.getAuthor().getId(), review.getProduct().getId(),
                review.getRating(), review.getDescription(), review.getDate());
    }

    @Override
    public Review findById(Long id) {
        return jdbcTemplate.queryObject(SELECT_REVIEW_BY_ID,
                new ReviewMapper(), id);
    }

    @Override
    public void update(Review review) {
        jdbcTemplate.update(UPDATE_REVIEW, review.getAuthor().getId(),
                review.getProduct().getId(), review.getRating(),
                review.getDescription(), review.getDate(), review.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_REVIEW, id);
    }

    @Override
    public List<Review> findAll() {
        return jdbcTemplate.queryObjects(SELECT_ALL_REVIEWS,
                new ReviewMapper());
    }

    @Override
    public List<Review> findByProduct(Long productId) {
        return jdbcTemplate.queryObjects(SELECT_REVIEW_BY_PRODUCT_ID,
                new ReviewMapper(), productId);
    }

    @Override
    public Review findByUserIdProductId(Long userId, Long productId) {
        return jdbcTemplate.queryObject(SELECT_REVIEW_BY_USER_PRODUCT_ID,
                new ReviewMapper(), userId, productId);
    }
}
