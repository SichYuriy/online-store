package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.domain.LineItem;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.LineItemDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.LineItemMapper;

import java.util.List;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class JdbcLineItemDao implements LineItemDao {

    private static final String INSERT_ITEM = "INSERT INTO `line_item`" +
            "(`order_id`, `product_id`, `temp_price`, `count`) VALUES(?, ?, ?, ?)";

    private static final String SELECT_ITEM_BY_ID = "SELECT * FROM `line_item` WHERE `id`=?";

    private static final String SELECT_ALL_ITEMS = "SELECT * FROM `line_item` ORDER BY `id` ASC";

    private static final String SELECT_ITEMS_BY_ORDER_ID = "SELECT * FROM `line_item` WHERE `order_id`=? " +
            "ORDER BY `id` ASC";

    private static final String UPDATE_ITEM = "UPDATE `line_item` " +
            "SET `order_id`=?, `product_id`=?, `temp_price`=?, `count`=? " +
            "WHERE `id`=?";

    private static final String DELETE_ITEM = "DELETE FROM `line_item` WHERE `id`=?";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcLineItemDao(ConnectionManager cm) {
        this.cm = cm;
        jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public Long create(LineItem item) {
        return jdbcTemplate.insert(INSERT_ITEM,
                item.getOrder().getId(), item.getProduct().getId(),
                item.getTempPrice(), item.getCount());
    }

    @Override
    public LineItem findById(Long id) {
        return jdbcTemplate.queryObject(SELECT_ITEM_BY_ID,
                new LineItemMapper(), id);
    }

    @Override
    public void update(LineItem item) {
        jdbcTemplate.update(UPDATE_ITEM, item.getOrder().getId(),
                item.getProduct().getId(), item.getTempPrice(),
                item.getCount(), item.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_ITEM, id);
    }

    @Override
    public List<LineItem> findAll() {
        return jdbcTemplate.queryObjects(SELECT_ALL_ITEMS,
                new LineItemMapper());
    }

    @Override
    public List<LineItem> findByOrder(Long orderId) {
        return jdbcTemplate.queryObjects(SELECT_ITEMS_BY_ORDER_ID,
                new LineItemMapper(), orderId);
    }
}
