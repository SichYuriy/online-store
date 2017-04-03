package com.gmail.at.sichyuriyy.onlinestore.entity;

import com.gmail.at.sichyuriyy.onlinestore.util.Pair;

import java.math.BigDecimal;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class Order {

    private Long id;
    private Timestamp date;
    private OrderStatus status;
    private User user;

    public Order() {
    }

    public Order(Long id) {
        this.id = id;
    }

    public Order(Long id, Timestamp date, OrderStatus status, User user) {
        this.id = id;
        this.date = date;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Objects.equals(id, order.id) &&
                Objects.equals(date, order.date) &&
                status == order.status &&
                Objects.equals(user, order.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, date, status, user);
    }
}
