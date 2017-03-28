package com.gmail.at.sichyuriyy.onlinestore.entity;

import java.math.BigDecimal;

/**
 * Created by Yuriy on 3/28/2017.
 */
public class LineItem {

    private Long id;
    private Order order;
    private Product product;
    private BigDecimal tempPrice;
    private Integer count;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public BigDecimal getTempPrice() {
        return tempPrice;
    }

    public void setTempPrice(BigDecimal tempPrice) {
        this.tempPrice = tempPrice;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
