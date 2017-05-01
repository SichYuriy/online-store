package com.gmail.at.sichyuriyy.onlinestore.persistance.dao.jdbc;

import com.gmail.at.sichyuriyy.onlinestore.domain.Order;
import com.gmail.at.sichyuriyy.onlinestore.domain.OrderStatus;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.OrderDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.OrderMapper;

import java.util.List;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class JdbcOrderDao implements OrderDao {

    private static final String SELECT_ALL_ORDERS = "SELECT * FROM `order` ORDER BY `date` DESC";

    private static final String SELECT_ORDER_BY_ID = "SELECT * FROM `order` WHERE `id`=?";

    private static final String SELECT_ORDERS_BY_USER_ID = "SELECT * FROM `order` WHERE `user_id`=? ORDER BY `date` DESC";

    private static final String SELECT_PART = "SELECT * FROM `order` ORDER BY `date` DESC LIMIT ? OFFSET ?";

    private static final String SELECT_PART_BY_STATUS = "SELECT * FROM `order` " +
            "WHERE `status`=? ORDER BY `date` DESC LIMIT ? OFFSET ?";

    private static final String INSERT_ORDER = "INSERT INTO `order`(`user_id`, `date`, `status`) VALUES(?, ?, ?)";

    private static final String UPDATE_ORDER = "UPDATE `order` " +
            "SET `user_id`=?, `date`=?, `status`=? WHERE `id`=?";

    private static final String DELETE_ORDER = "DELETE FROM `order` WHERE `id`=?";

    private ConnectionManager cm;
    private JdbcTemplate jdbcTemplate;

    public JdbcOrderDao(ConnectionManager cm) {
        this.cm = cm;
        jdbcTemplate = new JdbcTemplate(cm);
    }

    @Override
    public List<Order> findByUser(Long userId) {
        return jdbcTemplate.queryObjects(SELECT_ORDERS_BY_USER_ID,
                new OrderMapper(), userId);
    }

    @Override
    public List<Order> findPart(int limit, int offset) {
        return jdbcTemplate.queryObjects(SELECT_PART,
                new OrderMapper(), limit, offset);
    }

    @Override
    public List<Order> findByStatusPart(OrderStatus status, int limit, int offset) {
        return jdbcTemplate.queryObjects(SELECT_PART_BY_STATUS,
                new OrderMapper(), status.toString(), limit, offset);
    }

    @Override
    public Long create(Order order) {
        return jdbcTemplate.insert(INSERT_ORDER, order.getUser().getId(),
                order.getDate(), order.getStatus().toString());
    }

    @Override
    public Order findById(Long id) {
        return jdbcTemplate.queryObject(SELECT_ORDER_BY_ID,
                new OrderMapper(), id);
    }

    @Override
    public void update(Order order) {
        jdbcTemplate.update(UPDATE_ORDER, order.getUser().getId(),
                order.getDate(), order.getStatus().toString(), order.getId());
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_ORDER, id);
    }

    @Override
    public List<Order> findAll() {
        return jdbcTemplate.queryObjects(SELECT_ALL_ORDERS,
                new OrderMapper());
    }
}
