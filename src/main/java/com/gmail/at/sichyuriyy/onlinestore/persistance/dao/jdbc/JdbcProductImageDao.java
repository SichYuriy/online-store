package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.domain.ProductImage;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductImageDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.ProductImageMapper;

import java.util.List;

/**
 * Created by Yuriy on 3/30/2017.
 */
public class JdbcProductImageDao implements ProductImageDao {

    private static final String INSERT_IMAGE = "INSERT INTO `product_image`" +
            "(`product_id`, `image_url`, `small_image_url`) VALUES(?, ?, ?)";

    private static final String SELECT_ALL_IMAGES = "SELECT * FROM `product_image` ORDER BY `id` ASC";

    private static final String SELECT_IMAGE_BY_ID = "SELECT * FROM `product_image` WHERE `id`=?";

    private static final String SELECT_IMAGES_BY_PRODUCT_ID = "SELECT * FROM `product_image` WHERE `product_id`=? " +
            "ORDER BY `id` ASC";

    private static final String UPDATE_IMAGE = "UPDATE `product_image` " +
            "SET `product_id`=?, `image_url`=?, `small_image_url`=? WHERE `id`=?";

    private static final String DELETE_IMAGE = "DELETE FROM `product_image` WHERE `id`=?";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcProductImageDao(ConnectionManager cm) {
        this.cm = cm;
        jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public Long create(ProductImage image) {
        return jdbcTemplate.insert(INSERT_IMAGE, image.getProduct().getId(),
                image.getImageUrl(), image.getSmallVersionUrl());
    }

    @Override
    public ProductImage findById(Long id) {
        return jdbcTemplate.queryObject(SELECT_IMAGE_BY_ID,
                new ProductImageMapper(), id);
    }

    @Override
    public void update(ProductImage image) {
        jdbcTemplate.update(UPDATE_IMAGE, image.getProduct().getId(),
                image.getImageUrl(), image.getSmallVersionUrl(), image.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_IMAGE, id);
    }

    @Override
    public List<ProductImage> findAll() {
        return jdbcTemplate.queryObjects(SELECT_ALL_IMAGES,
                new ProductImageMapper());
    }

    @Override
    public List<ProductImage> findByProduct(Long productId) {
        return jdbcTemplate.queryObjects(SELECT_IMAGES_BY_PRODUCT_ID,
                new ProductImageMapper(), productId);
    }
}
