package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.entity.Product;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDynamicFilter;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.ProductMapper;

import java.util.List;

/**
 * Created by Yuriy on 3/30/2017.
 */
public class JdbcProductDao implements ProductDao {

    private static final String SELECT_ALL_PRODUCT = "SELECT * FROM `product` ORDER BY `id` ASC";

    private static final String SELECT_PRODUCTS_COUNT = "SELECT COUNT(*) FROM `product` WHERE `category_id`=? ";

    private static final String SELECT_PRODUCTS_BY_CATEGORY_ID = "SELECT * FROM `product`" +
            "WHERE `category_id`=?  ORDER BY `id` ASC LIMIT ? OFFSET ?";

    private static final String INSERT_PRODUCT = "INSERT INTO `product`" +
            "(`category_id`, `title`, `description`, `price`, `count`, `enabled`, " +
            "`votes_count`, `avg_rating`, `main_image_url`) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";

    private static final String SELECT_PRODUCT_BY_ID = "SELECT * FROM `product` WHERE `id`=?";

    private static final String UPDATE_PRODUCT = "UPDATE `product` " +
            "SET `category_id`=?, `title`=?, `description`=?, `price`=?, `count`=?, `enabled`=?, " +
            "`votes_count`=?, `avg_rating`=?, `main_image_url`=? WHERE `id`=?";

    private static final String DELETE_PRODUCT = "DELETE FROM `product` WHERE `id`=?";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcProductDao(ConnectionManager cm) {
        this.cm = cm;
        jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public Long create(Product product) {
        return jdbcTemplate.insert(INSERT_PRODUCT, product.getCategory().getId(),
                product.getTitle(), product.getDescription(), product.getPrice(),
                product.getCount(), product.getEnabled(), product.getVotesCount(),
                product.getAvgRating(), product.getMainImageUrl());
    }

    @Override
    public Product findById(Long id) {
        return jdbcTemplate.queryObject(SELECT_PRODUCT_BY_ID,
                new ProductMapper(), id);
    }

    @Override
    public void update(Product product) {
        jdbcTemplate.update(UPDATE_PRODUCT, product.getCategory().getId(),
                product.getTitle(), product.getDescription(), product.getPrice(),
                product.getCount(), product.getEnabled(), product.getVotesCount(),
                product.getAvgRating(), product.getMainImageUrl(), product.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_PRODUCT, id);
    }

    @Override
    public List<Product> findAll() {
        return jdbcTemplate.queryObjects(SELECT_ALL_PRODUCT,
                new ProductMapper());
    }

    @Override
    public List<Product> findByDynamicFilter(ProductDynamicFilter filter) {
        return jdbcTemplate.queryObjects(filter.getQuery(),
                new ProductMapper(), filter.getParams());
    }

    @Override
    public List<Product> findByCategory(Long categoryId, int limit, int offset) {
        return jdbcTemplate.queryObjects(SELECT_PRODUCTS_BY_CATEGORY_ID,
                new ProductMapper(), categoryId, limit, offset);
    }

    @Override
    public int getProductsCount(Long categoryId) {
        return jdbcTemplate.queryObject(SELECT_PRODUCTS_COUNT,
                (rs) -> rs.getInt(1), categoryId);
    }
}
