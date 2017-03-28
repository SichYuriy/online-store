package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.entity.LineItem;

import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface LineItemDao extends Dao<LineItem, Long> {

    List<LineItem> findByOrder(Long orderId);
}
