package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.domain.CartItem;
import com.gmail.at.sichyuriyy.onlinestore.domain.Product;
import com.gmail.at.sichyuriyy.onlinestore.domain.ShoppingCart;
import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.CartItemDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.ProductDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.service.CartService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by Yuriy on 4/30/2017.
 */
public class CartServiceImpl implements CartService {


    private CartItemDao cartItemDao;
    private ProductDao productDao;

    private TransactionManager transactionManager = ServiceLocator.INSTANCE.get(TransactionManager.class);

    public CartServiceImpl(CartItemDao cartItemDao, ProductDao productDao) {
        this.cartItemDao = cartItemDao;
        this.productDao = productDao;
    }

    @Override
    public ShoppingCart findByUser(Long userId) {
        ShoppingCart shoppingCart = new ShoppingCart();
        transactionManager.tx(() -> {
            shoppingCart.setCartItems(findCartItemsByUser(userId));
            BigDecimal total = BigDecimal.ZERO;
            for (CartItem item: shoppingCart.getCartItems()) {
                BigDecimal price = item.getProduct().getPrice();
                BigDecimal count = new BigDecimal(item.getCount());
                total = total.add(price.multiply(count));
            }
            shoppingCart.setTotal(total);
        });
        return shoppingCart;
    }

    @Override
    public boolean addProduct(Long userId, Long productId) {
        boolean[] result = new boolean[1];
        transactionManager.tx(() -> {
            CartItem cartItem = cartItemDao.findByUserProduct(userId, productId);
            Product product = productDao.findById(productId);
            if (!product.getEnabled()) {
                return;
            }
            if (cartItem == null) {
                result[0] = tryAddNewProduct(userId, product);
            } else {
                result[0] = tryAddProduct(cartItem, product);
            }
        });
        return result[0];
    }

    private boolean tryAddNewProduct(Long userId, Product product) {
        if (product.getCount() == 0) {
            return false;
        }
        CartItem cartItem = new CartItem();
        cartItem.setUser(new User(userId));
        cartItem.setProduct(product);
        cartItem.setCount(1);
        cartItemDao.create(cartItem);
        return true;
    }

    private boolean tryAddProduct(CartItem cartItem, Product product) {
        if (product.getCount() <= cartItem.getCount()) {
            return false;
        }
        cartItem.setCount(cartItem.getCount() + 1);
        cartItemDao.update(cartItem);
        return true;
    }

    @Override
    public boolean removeProduct(Long userId, Long productId) {
        final boolean[] result = new boolean[1];
        transactionManager.tx(() -> {
            CartItem cartItem = cartItemDao.findByUserProduct(userId, productId);
            if (cartItem.getCount()  > 1) {
                cartItem.setCount(cartItem.getCount() - 1);
                cartItemDao.update(cartItem);
                result[0] = true;
            } else if (cartItem.getCount() > 0) {
                cartItemDao.delete(cartItem.getId());
            }
        });
        return result[0];
    }

    @Override
    public void clearCart(Long userId) {
        cartItemDao.deleteByUser(userId);
    }

    @Override
    public List<CartItem> findCartItemsByUser(Long userId) {
        Object[] result = new Object[1];
        transactionManager.tx(() -> {
            List<CartItem> cartItems = cartItemDao.findByUser(userId);
            for (CartItem cartItem: cartItems) {
                cartItem.setProduct(productDao.findById(cartItem.getProduct().getId()));
            }
            result[0] = cartItems;
        });
        return (List<CartItem>)result[0];
    }
}
