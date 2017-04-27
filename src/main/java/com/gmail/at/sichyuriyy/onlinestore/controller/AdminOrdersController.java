package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.AjaxRedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.entity.Order;
import com.gmail.at.sichyuriyy.onlinestore.entity.OrderStatus;
import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.security.AccessDeniedException;
import com.gmail.at.sichyuriyy.onlinestore.service.OrderService;
import com.gmail.at.sichyuriyy.onlinestore.service.UserService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class AdminOrdersController extends Controller {

    private OrderService orderService = ServiceLocator.INSTANCE.get(OrderService.class);
    private UserService userService = ServiceLocator.INSTANCE.get(UserService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Long requestUserId = reqService.getLong("userId");

        List<Order> orders;
        if (requestUserId != null) {
            orders = orderService.findByUser(requestUserId);
        } else {
            orders = orderService.findAll();
        }

        reqService.setPageAttribute("orders", orders);
        respService.setResponseResolver(new RenderResolver("/pages/admin/orders.jsp"));
    }

    @Override
    public void doPut(RequestService reqService, ResponseService respService) {
        Long orderId = reqService.getLong("orderId");
        OrderStatus status = OrderStatus.valueOf(reqService.getString("status"));

        orderService.changeOrderStatus(orderId, status);
        respService.setResponseResolver(new AjaxRedirectResolver("/admin/orders"));
    }
}
