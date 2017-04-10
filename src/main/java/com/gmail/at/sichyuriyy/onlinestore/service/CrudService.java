package com.gmail.at.sichyuriyy.onlinestore.service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yuriy on 4/7/2017.
 */
public interface CrudService<T, PK extends Serializable> {

    PK create(T obj);
    T findById(PK id);
    void update(T obj);
    List<T> findAll();

}
