package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.domain.Order;
import com.gmail.at.sichyuriyy.onlinestore.domain.OrderStatus;

import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface OrderDao extends Dao<Order, Long>{

    List<Order> findByUser(Long userId);
    List<Order> findPart(int limit, int offset);
    List<Order> findByStatusPart(OrderStatus status, int limit, int offset);
}
