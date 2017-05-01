package com.gmail.at.sichyuriyy.onlinestore.controller;

import com.gmail.at.sichyuriyy.onlinestore.dispatcher.Controller;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.RequestService;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.AjaxErrorResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.AjaxRedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RedirectResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.responseresolver.RenderResolver;
import com.gmail.at.sichyuriyy.onlinestore.dispatcher.ResponseService;
import com.gmail.at.sichyuriyy.onlinestore.domain.ShoppingCart;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.service.CartService;
import com.gmail.at.sichyuriyy.onlinestore.service.OrderService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;
import org.apache.logging.log4j.LogManager;

/**
 * Created by Yuriy on 5/1/2017.
 */
public class ShoppingCartController extends Controller {

    private CartService cartService = ServiceLocator.INSTANCE.get(CartService.class);
    private OrderService orderService = ServiceLocator.INSTANCE.get(OrderService.class);


    @Override
    public void doGet(RequestService reqService, ResponseService respService) {
        User user = reqService.getUser();
        ShoppingCart cart = cartService.findByUser(user.getId());
        reqService.setPageAttribute("shoppingCart", cart);
        respService.setResponseResolver(new RenderResolver("/pages/user/cart.jsp"));
    }

    @Override
    public void doPost(RequestService reqService, ResponseService respService) {
        User user = reqService.getUser();

        boolean result = orderService.makeOrder(user.getId());

        if (result) {
            respService.setResponseResolver(new RedirectResolver("/user/orders"));
        } else {
            respService.setResponseResolver(new RenderResolver("/pages/user/order-fail.jsp"));
        }
    }

    @Override
    public void doDelete(RequestService reqService, ResponseService respService) {
        User user = reqService.getUser();
        LogManager.getLogger().info("clear cart");
        cartService.clearCart(user.getId());
        respService.setResponseResolver(
                new RedirectResolver("/user/shoppingCart")
        );
    }
}
