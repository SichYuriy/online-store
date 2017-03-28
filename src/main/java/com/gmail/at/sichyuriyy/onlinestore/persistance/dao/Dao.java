package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface Dao<T, PK extends Serializable> {

    PK create(T obj);
    T findById(PK key);
    void update(T obj);
    void delete(PK key);
    List<T> findAll();
}
