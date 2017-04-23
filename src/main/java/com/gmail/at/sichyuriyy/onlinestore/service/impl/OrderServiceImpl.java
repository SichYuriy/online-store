package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.entity.*;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.*;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.Transaction;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.OrderService;

import javax.sound.sampled.Line;
import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class OrderServiceImpl extends AbstractCrudService<Order, Long> implements OrderService {

    private OrderDao orderDao;
    private UserDao userDao;
    private LineItemDao lineItemDao;
    private ProductDao productDao;
    private ConnectionManager cm;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, LineItemDao lineItemDao, ProductDao productDao, ConnectionManager cm) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.lineItemDao = lineItemDao;
        this.productDao = productDao;
        this.cm = cm;
    }

    @Override
    public void create(Order order) {
        Long id = orderDao.create(order);
        order.setId(id);
    }

    @Override
    public List<Order> findByUser(Long userId) {
        final Object[] result = new Object[1];
        Transaction.tx(cm, () -> {
            List<Order> orders = orderDao.findByUser(userId);
            User user = userDao.findById(userId);
            for (Order order: orders) {
                order.setUser(user);
            }
            result[0] = orders;
        });

        return (List<Order>) result[0];
    }

    @Override
    public List<Order> findAll() {
        final Object[] result = new Object[1];
        Transaction.tx(cm, () -> {
            List<Order> orders = orderDao.findAll();
            for (Order order: orders) {
                order.setUser(userDao.findById(order.getUser().getId()));
            }
            result[0] = orders;
        });

        return (List<Order>) result[0];
    }

    @Override
    public List<Order> findPart(int limit, int offset) {
        return orderDao.findPart(limit, offset);
    }

    @Override
    public List<Order> findByStatusPart(OrderStatus status, int limit, int offset) {
        return orderDao.findByStatusPart(status, limit, offset);
    }

    @Override
    public void changeOrderStatus(Long id, OrderStatus status) {
        Transaction.tx(cm, () -> {
            Order order = orderDao.findById(id);
            order.setStatus(status);
            orderDao.update(order);
        });
    }

    @Override
    public List<LineItem> findItems(Long orderId) {
        final Object[] result = new Object[1];
        Transaction.tx(cm, () -> {

            List<LineItem> items = lineItemDao.findByOrder(orderId);
            for (LineItem item: items) {
                item.setProduct(productDao.findById(item.getProduct().getId()));
            }
            result[0] = items;
        });

        return (List<LineItem>) result[0];
    }

    @Override
    public Double countTotal(List<LineItem> lineItems) {
        return lineItems.stream()
                .mapToDouble((item) -> item.getCount() * item.getTempPrice().doubleValue())
                .sum();
    }

    @Override
    public void cancelOrder(Long id) {
        Transaction.tx(cm, () -> {
            Order order = orderDao.findById(id);
            if (order.getStatus().equals(OrderStatus.CREATED)) {
                order.setStatus(OrderStatus.CANCELED);
                orderDao.update(order);
            }
        });
    }



    @Override
    public Dao<Order, Long> getBackingDao() {
        return orderDao;
    }


}
