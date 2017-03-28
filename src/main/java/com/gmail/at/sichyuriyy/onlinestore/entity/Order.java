package com.gmail.at.sichyuriyy.onlinestore.entity;

import com.gmail.at.sichyuriyy.onlinestore.util.Pair;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class Order {

    private Long id;
    private Date date;
    private OrderStatus status;
    private User user;
    private List<LineItem> lineItems;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<LineItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(List<LineItem> lineItems) {
        this.lineItems = lineItems;
    }
}
