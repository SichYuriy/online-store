package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.AjaxRedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.domain.Order;
import com.gmail.at.sichyuriyy.onlinestore.service.OrderService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class AdminOrdersController extends Controller {

    private OrderService orderService = ServiceLocator.INSTANCE.get(OrderService.class);

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

        orderService.overdueOrder(orderId);
        respService.setResponseResolver(new AjaxRedirectResolver("/admin/orders"));
    }
}
