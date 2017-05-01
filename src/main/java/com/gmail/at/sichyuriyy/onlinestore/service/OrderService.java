package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.domain.LineItem;
import com.gmail.at.sichyuriyy.onlinestore.domain.Order;
import com.gmail.at.sichyuriyy.onlinestore.domain.OrderStatus;

import java.util.List;

/**
 * Created by Yuriy on 4/7/2017.
 */
public interface OrderService extends CrudService<Order, Long> {

    List<Order> findByUser(Long userId);
    List<Order> findPart(int limit, int offset);
    List<Order> findByStatusPart(OrderStatus status, int limit, int offset);

    List<LineItem> findItems(Long orderId);
    Double countTotal(List<LineItem> lineItems);

    void cancelOrder(Long id);
    void payOrder(Long id);
    void overdueOrder(Long id);

    boolean makeOrder(Long userId);

}
