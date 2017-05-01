package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.domain.Order;
import com.gmail.at.sichyuriyy.onlinestore.domain.OrderStatus;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class OrderMapper implements RowMapper<Order> {

    @Override
    public Order map(ResultSet rs) throws SQLException {
        Order order = new Order();
        order.setId(rs.getLong("id"));
        order.setDate(rs.getTimestamp("date"));
        order.setStatus(OrderStatus.valueOf(rs.getString("status")));
        order.setUser(new User(rs.getLong("user_id")));
        return order;
    }
}
