package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.AjaxErrorResolver;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.service.CartService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 5/1/2017.
 */
public class CartItemsController extends Controller {

    private CartService cartService = ServiceLocator.INSTANCE.get(CartService.class);

    @Override
    public void doPost(RequestService reqService, ResponseService respService) {
        Long productId = reqService.getLong("productId");
        User user = reqService.getUser();

        boolean result = cartService.addProduct(user.getId(), productId);
        if (!result) {
            respService.setResponseResolver(new AjaxErrorResolver("error.add_product"));
        }
    }

    @Override
    public void doDelete(RequestService reqService, ResponseService respService) {
        Long productId = reqService.getLong("productId");
        User user = reqService.getUser();

        cartService.removeProduct(user.getId(), productId);
    }
}
