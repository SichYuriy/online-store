package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseResolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.entity.LineItem;
import com.gmail.at.sichyuriyy.onlinestore.entity.Order;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.security.AccessDeniedException;
import com.gmail.at.sichyuriyy.onlinestore.service.OrderService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.util.List;

/**
 * Created by Yuriy on 4/23/2017.
 */
public class OrderViewController extends Controller {

    private OrderService orderService = ServiceLocator.INSTANCE.get(OrderService.class);

    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        Long id = reqService.getLong("id");
        Order order = orderService.findById(id);

        if (!reqService.getUser().getId().equals(order.getUser().getId())
                && !reqService.getRequest().isUserInRole("ADMINISTRATOR")) {
            throw new AccessDeniedException();
        }

        List<LineItem> lineItems = orderService.findItems(id);
        Double total = orderService.countTotal(lineItems);
        reqService.setPageAttribute("lineItems", lineItems);
        reqService.setPageAttribute("total", total);
        respService.setResponseResolver(new RenderResolver("/pages/order.jsp"));

    }
}
