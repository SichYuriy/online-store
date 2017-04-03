package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.entity.LineItem;
import com.gmail.at.sichyuriyy.onlinestore.entity.Order;
import com.gmail.at.sichyuriyy.onlinestore.entity.Product;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class LineItemMapper implements RowMapper<LineItem> {

    @Override
    public LineItem map(ResultSet rs) throws SQLException {
        LineItem item = new LineItem();
        item.setId(rs.getLong("id"));
        item.setCount(rs.getInt("count"));
        item.setTempPrice(rs.getBigDecimal("temp_price"));
        item.setOrder(new Order(rs.getLong("order_id")));
        item.setProduct(new Product(rs.getLong("product_id")));
        return item;
    }
}
