package com.gmail.at.sichyuriyy.onlinestore.entity;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Created by Yuriy on 3/28/2017.
 */
public class LineItem {

    private Long id;
    private Order order;
    private Product product;
    private BigDecimal tempPrice;
    private Integer count;

    public LineItem() {
    }

    public LineItem(Long id) {
        this.id = id;
    }

    public LineItem(Long id, Order order, Product product,
                    BigDecimal tempPrice, Integer count) {
        this.id = id;
        this.order = order;
        this.product = product;
        this.tempPrice = tempPrice;
        this.count = count;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LineItem lineItem = (LineItem) o;
        return Objects.equals(id, lineItem.id) &&
                Objects.equals(order, lineItem.order) &&
                Objects.equals(product, lineItem.product) &&
                Objects.equals(0, tempPrice.compareTo(lineItem.getTempPrice())) &&
                Objects.equals(count, lineItem.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, order, product, tempPrice, count);
    }
}
