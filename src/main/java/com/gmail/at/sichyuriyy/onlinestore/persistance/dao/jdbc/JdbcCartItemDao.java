package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.domain.CartItem;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.CartItemDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.CartItemMapper;

import java.util.List;

/**
 * Created by Yuriy on 4/30/2017.
 */
public class JdbcCartItemDao implements CartItemDao {

    private static final String INSERT_CART_ITEM = "INSERT INTO `cart_item`(`user_id`, `product_id`, `count`)" +
            " VALUES(?, ?, ?)";

    private static final String SELECT_CART_ITEM_BY_USER_PRODUCT_ID = "SELECT * FROM `cart_item` " +
            "WHERE `user_id`=? AND `product_id`=? ORDER BY `id`";

    private static final String SELECT_CART_ITEM_BY_ID = "SELECT * FROM `cart_item` WHERE `id`=? " +
            "ORDER BY `id` ASC";

    private static final String SELECT_ALL_CART_ITEMS = "SELECT * FROM `cart_item` ORDER BY `id` ASC";

    private static final String UPDATE_CART_ITEM = "UPDATE `cart_item` SET `user_id`=?, `product_id`=?, " +
            "`count`=? WHERE `id`=?";

    private static final String DELETE_CART_ITEM = "DELETE FROM `cart_item` WHERE `id`=?";

    private static final String DELETE_CART_ITEMS_BY_USER_ID = "DELETE FROM `cart_item` WHERE `user_id`=?";

    private static final String SELECT_CART_ITEMS_BY_USER_ID = "SELECT * FROM `cart_item` WHERE `user_id`=? " +
            "ORDER BY `id`";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcCartItemDao(ConnectionManager cm) {
        this.cm = cm;
        this.jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public Long create(CartItem item) {
        return jdbcTemplate.insert(INSERT_CART_ITEM, item.getUser().getId(),
                item.getProduct().getId(), item.getCount());
    }

    @Override
    public CartItem findById(Long id) {
        return jdbcTemplate.queryObject(SELECT_CART_ITEM_BY_ID,
                new CartItemMapper(), id);
    }

    @Override
    public void update(CartItem item) {
        jdbcTemplate.update(UPDATE_CART_ITEM,
                item.getUser().getId(), item.getProduct().getId(), item.getCount(), item.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_CART_ITEM, id);
    }

    @Override
    public List<CartItem> findAll() {
        return jdbcTemplate.queryObjects(SELECT_ALL_CART_ITEMS,
                new CartItemMapper());
    }

    @Override
    public List<CartItem> findByUser(Long userId) {
        return jdbcTemplate.queryObjects(SELECT_CART_ITEMS_BY_USER_ID,
                new CartItemMapper(), userId);
    }

    @Override
    public void deleteByUser(Long userId) {
        jdbcTemplate.update(DELETE_CART_ITEMS_BY_USER_ID, userId);
    }

    @Override
    public CartItem findByUserProduct(Long userId, Long productId) {
        return jdbcTemplate.queryObject(SELECT_CART_ITEM_BY_USER_PRODUCT_ID,
                new CartItemMapper(), userId, productId);
    }
}
