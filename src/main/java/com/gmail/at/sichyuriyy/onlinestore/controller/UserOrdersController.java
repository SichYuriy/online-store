package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.AjaxRedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.domain.Order;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
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
    public void doGet(RequestService reqService, ResponseService respService) {
        User user = reqService.getUser();

        List<Order> orders = orderService.findByUser(user.getId());

        reqService.setPageAttribute("orders", orders);
        respService.setResponseResolver(new RenderResolver("/pages/user/orders.jsp"));
    }

    @Override
    public void doPost(RequestService reqService, ResponseService respService) {
        Long orderId = reqService.getLong("orderId");
        User user = reqService.getUser();

        Order order = orderService.findById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException();
        }

        orderService.payOrder(orderId);
        respService.setResponseResolver(new AjaxRedirectResolver("/user/orders"));
    }

    @Override
    public void doPut(RequestService reqService, ResponseService respService) {
        Long orderId = reqService.getLong("orderId");
        User user = reqService.getUser();

        Order order = orderService.findById(orderId);
        if (!order.getUser().getId().equals(user.getId())) {
            throw new AccessDeniedException();
        }

        orderService.cancelOrder(orderId);
        respService.setResponseResolver(new AjaxRedirectResolver("/user/orders"));
    }
}
