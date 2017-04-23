package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Order;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.security.AccessDeniedException;
import com.gmail.at.sichyuriyy.onlinestore.service.OrderService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class UserOrdersController extends Controller {

    private OrderService orderService = ServiceLocator.INSTANCE.get(OrderService.class);

    @Override
    public void doGet(RequestService reqService) {
        User user = reqService.getUser();

        List<Order> orders = orderService.findByUser(user.getId());

        reqService.setPageAttribute("orders", orders);
        reqService.setRenderPage("/pages/user/orders.jsp");
    }

    @Override
    public void doPut(RequestService reqService) {
        Long orderId = reqService.getLong("orderId");
        User user = reqService.getUser();

        Order order = orderService.findById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException();
        }

        orderService.cancelOrder(orderId);
        reqService.setAjaxRedirectPath("/user/orders");
    }
}
