package com.gmail.at.sichyuriyy.onlinestore.persistance;

import com.gmail.at.sichyuriyy.onlinestore.entity.*;

import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yuriy on 3/31/2017.
 */
public class TestData {

    private Map<Long, Category> categoryTable = new HashMap<>();
    private Map<Long, Product> productTable = new HashMap<>();
    private Map<Long, User> userTable = new HashMap<>();
    private Map<Long, Order> orderTable = new HashMap<>();
    private Map<Long, LineItem> lineItemTable = new HashMap<>();
    private Map<Long, ProductImage> productImageTable = new HashMap<>();
    private Map<Long, Review> reviewTable = new HashMap<>();


    public TestData() {
        Category category1 = new Category(1L, "category1");
        Category category2 = new Category(2L, "category2");
        Category category3 = new Category(3L, "category3");

        User user1 = new User(1L, "user1", "111111", false);
        User user2 = new User(2L, "user2", "222222", true);
        User user3 = new User(3L, "user3", "333333", false);
        User user4 = new User(4L, "user4", "444444", false);

        Product product1 = new Product(1L, "pr1_t", "pr1_d", category1,
                new BigDecimal(11), 1, 1, 4d, "https://img1", true);
        Product product2 = new Product(2L, "pr2_t", "pr2_d", category2,
                new BigDecimal(22), 2, 2, 4d, "https://img2", true);
        Product product3 = new Product(3L, "pr3_t", "pr3_d", category2,
                new BigDecimal(33), 1, 1, 3d, "https://img3", true);
        Product product4 = new Product(4L, "pr4_t", "pr4_d", category2,
                new BigDecimal(44), 1, 1, 3d, "https://img4", true);

        ProductImage image1 = new ProductImage(1L, product1,"https://pr1_img1", "https://pr1_s_img1");
        ProductImage image2 = new ProductImage(2L, product1, "https://pr1_img2", "https://pr1_s_img2");
        ProductImage image3 = new ProductImage(3L, product2, "https://pr2_img1", "https://pr2_s_img1");

        Review review1 = new Review(1L, user1, product1, 4d, "rev1", new Timestamp(2017 - 1900, 2, 29, 11, 30, 45, 0));
        Review review2 = new Review(2L, user1, product2, 5d, "rev2", new Timestamp(2017 - 1900, 2, 29, 11, 40, 45, 0));
        Review review3 = new Review(3L, user2, product2, 3d, "rev3", new Timestamp(2017 - 1900, 2, 29, 11, 41, 45, 0));
        Review review4 = new Review(4L, user2, product3, 3d, "rev4", new Timestamp(2017 - 1900, 2, 30, 11, 30, 45, 0));

        Order order1 = new Order(1L, new Timestamp(2017 - 1900, 2, 29, 11, 30, 45, 0), OrderStatus.PAID, user1);
        Order order2 = new Order(2L, new Timestamp(2017 - 1900, 2, 30, 11, 30, 45, 0), OrderStatus.CANCELED, user1);
        Order order3 = new Order(3L, new Timestamp(2017 - 1900, 2, 29, 12, 30, 45, 0), OrderStatus.OVERDUE, user2);
        Order order4 = new Order(4L, new Timestamp(2017 - 1900, 3, 29, 12, 30, 45, 0), OrderStatus.PAID, user2);

        LineItem item1 = new LineItem(1L, order1, product1, new BigDecimal(11), 1);
        LineItem item2 = new LineItem(2L, order1, product2, new BigDecimal(22), 1);
        LineItem item3 = new LineItem(3L, order2, product3, new BigDecimal(33), 1);
        LineItem item4 = new LineItem(4L, order3, product3, new BigDecimal(32), 1);

        categoryTable.put(1L, category1);
        categoryTable.put(2L, category2);
        categoryTable.put(3L, category3);

        userTable.put(1L, user1);
        userTable.put(2L, user2);
        userTable.put(3L, user3);
        userTable.put(4L, user4);

        productTable.put(1L, product1);
        productTable.put(2L, product2);
        productTable.put(3L, product3);
        productTable.put(4L, product4);

        productImageTable.put(1L, image1);
        productImageTable.put(2L, image2);
        productImageTable.put(3L, image3);

        reviewTable.put(1L, review1);
        reviewTable.put(2L, review2);
        reviewTable.put(3L, review3);
        reviewTable.put(4L, review4);

        orderTable.put(1L, order1);
        orderTable.put(2L, order2);
        orderTable.put(3L, order3);
        orderTable.put(4L, order4);

        lineItemTable.put(1L, item1);
        lineItemTable.put(2L, item2);
        lineItemTable.put(3L, item3);
        lineItemTable.put(4L, item4);
    }

    public Category getCategory(Long id) {
        return categoryTable.get(id);
    }

    public User getUser(Long id) {
        return userTable.get(id);
    }

    public Product getProduct(Long id) {
        return productTable.get(id);
    }

    public ProductImage getProductImage(Long id) {
        return productImageTable.get(id);
    }

    public Review getReview(Long id) {
        return reviewTable.get(id);
    }

    public Order getOrder(Long id) {
        return orderTable.get(id);
    }

    public LineItem getLineItem(Long id) {
        return lineItemTable.get(id);
    }

}
