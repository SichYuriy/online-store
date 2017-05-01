package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.domain.*;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.*;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.SQLRuntimeException;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.TransactionFailedException;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.CartService;
import com.gmail.at.sichyuriyy.onlinestore.service.OrderService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class OrderServiceImpl extends AbstractCrudService<Order, Long> implements OrderService {

    private OrderDao orderDao;
    private UserDao userDao;
    private LineItemDao lineItemDao;
    private ProductDao productDao;
    private TransactionManager transactionManager = ServiceLocator.INSTANCE.get(TransactionManager.class);

    private CartService cartService;

    public OrderServiceImpl(OrderDao orderDao, UserDao userDao, LineItemDao lineItemDao, ProductDao productDao,
                             CartService cartService) {
        this.orderDao = orderDao;
        this.userDao = userDao;
        this.lineItemDao = lineItemDao;
        this.productDao = productDao;
        this.cartService = cartService;
    }

    @Override
    public void create(Order order) {
        Long id = orderDao.create(order);
        order.setId(id);
    }

    @Override
    public List<Order> findByUser(Long userId) {
        final Object[] result = new Object[1];
        transactionManager.tx(() -> {
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
        transactionManager.tx(() -> {
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
    public List<LineItem> findItems(Long orderId) {
        final Object[] result = new Object[1];
        transactionManager.tx(() -> {

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
        changeStatus(id, OrderStatus.CANCELED);
    }

    @Override
    public void overdueOrder(Long id) {
        changeStatus(id, OrderStatus.OVERDUE);
    }

    @Override
    public void payOrder(Long id) {
        changeStatus(id, OrderStatus.PAID);
    }

    @Override
    public boolean makeOrder(Long userId) {
        final boolean[] result = new boolean[1];
        try {
            transactionManager.tx(() -> {
                List<CartItem> cartItems = cartService.findCartItemsByUser(userId);
                if (!checkItemsState(cartItems)) {
                    return;
                }

                Order order = createNewOrder(userId);
                create(order);

                for (CartItem cartItem: cartItems) {
                    LineItem lineItem = createNewLineItem(order, cartItem);
                    lineItemDao.create(lineItem);
                    Product product = cartItem.getProduct();
                    product.setCount(product.getCount() - cartItem.getCount());
                    productDao.update(product);
                }
                result[0] = true;
            });
        } catch (SQLRuntimeException | TransactionFailedException e) {
            return result[0] = false;
        }

        cartService.clearCart(userId);
        return result[0];
    }

    private boolean checkItemsState(List<CartItem> cartItems) {
        if (cartItems.size() == 0) {
            return false;
        }
        for (CartItem item: cartItems) {
            if (item.getProduct().getCount() < item.getCount()
                    || !item.getProduct().getEnabled()) {
                return false;
            }
        }
        return  true;
    }

    private Order createNewOrder(Long userId) {
        Order order = new Order();
        order.setUser(new User(userId));
        order.setStatus(OrderStatus.CREATED);
        order.setDate(new Timestamp(new Date().getTime()));
        return order;
    }

    private LineItem createNewLineItem(Order order, CartItem cartItem) {
        LineItem lineItem = new LineItem();
        lineItem.setOrder(order);
        lineItem.setProduct(cartItem.getProduct());
        lineItem.setCount(cartItem.getCount());
        lineItem.setTempPrice(cartItem.getProduct().getPrice());
        return lineItem;
    }

    private void changeStatus(Long orderId, OrderStatus status) {
        transactionManager.tx(() -> {
            Order order = orderDao.findById(orderId);
            if (order.getStatus().equals(OrderStatus.CREATED)) {
                order.setStatus(status);
                orderDao.update(order);
            }
        });
    }

    @Override
    public Dao<Order, Long> getBackingDao() {
        return orderDao;
    }


}
