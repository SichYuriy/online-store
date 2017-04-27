package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.entity.Review;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ReviewDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.UserDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.Transaction;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.ReviewService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import jdk.nashorn.internal.runtime.options.LoggingOption;
import org.apache.logging.log4j.LogManager;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Yuriy on 4/22/2017.
 */
public class ReviewServiceImpl extends AbstractCrudService<Review, Long> implements ReviewService {

    private TransactionManager transactionManager = ServiceLocator.INSTANCE.get(TransactionManager.class);
    private ReviewDao reviewDao;
    private UserDao userDao;
    private ProductDao productDao;

    public ReviewServiceImpl(ReviewDao reviewDao, UserDao userDao, ProductDao productDao) {
        this.reviewDao = reviewDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }

    @Override
    public void create(Review review) {
        LogManager.getLogger().info("inserted0");
        transactionManager.tx(() -> {
            if (canVote(review.getAuthor().getId())) {
                review.setDate(new Timestamp(new Date().getTime()));
                Long id = reviewDao.create(review);
                review.setId(id);
                LogManager.getLogger().info("inserted1");
                Product product = productDao.findById(review.getProduct().getId());
                double rating = product.getAvgRating() == null ? 0 : product.getAvgRating();
                LogManager.getLogger().info("inserted2");
                rating = rating * product.getVotesCount() + review.getRating();
                rating /= product.getVotesCount() + 1;
                product.setVotesCount(product.getVotesCount() + 1);
                product.setAvgRating(rating);

                productDao.update(product);
                LogManager.getLogger().info("inserted3");
           }
        });
    }

    @Override
    public void update(Review review) {
        transactionManager.tx(() -> {
            Review oldReview = reviewDao.findById(review.getId());
            review.setDate(new Timestamp(new Date().getTime()));
            reviewDao.update(review);

            Product product = productDao.findById(review.getProduct().getId());
            double rating = product.getAvgRating() == null ? 0 : product.getAvgRating();

            rating = rating * product.getVotesCount() - oldReview.getRating() + review.getRating();
            rating /= product.getVotesCount();
            product.setAvgRating(rating);

            productDao.update(product);
        });
    }

    @Override
    public boolean delete(Long id) {
        transactionManager.tx(() -> {
            Review oldReview = reviewDao.findById(id);
            reviewDao.delete(id);

            Product product = productDao.findById(oldReview.getProduct().getId());
            double rating = product.getAvgRating() == null ? 0 : product.getAvgRating();

            rating = rating * product.getVotesCount() - oldReview.getRating();
            if (product.getVotesCount() - 1 == 0) {
                rating = 0;
            } else {
                rating /= product.getVotesCount() - 1;
            }

            product.setVotesCount(product.getVotesCount() - 1);
            product.setAvgRating(rating);

            productDao.update(product);
        });
        return true;
    }

    @Override
    public Dao<Review, Long> getBackingDao() {
        return reviewDao;
    }

    @Override
    public List<Review> findByProductId(Long productId) {
        final Object[] result = new Object[1];
        transactionManager.tx(() -> {
            List<Review> reviews = reviewDao.findByProduct(productId);
            for (Review review: reviews) {
                review.setAuthor(userDao.findById(review.getAuthor().getId()));
            }
            result[0] = reviews;
        });

        return (List<Review>) result[0];
    }

    @Override
    public Review findByUserIdProductId(Long userId, Long productId) {
        return reviewDao.findByUserIdProductId(userId, productId);
    }

    @Override
    public boolean canVote(Long userId) {
        if (userId == null) {
            return false;
        }
        User user = userDao.findById(userId);
        if (user == null) {
            return false;
        }
        return !user.getBlackList();
    }

}
