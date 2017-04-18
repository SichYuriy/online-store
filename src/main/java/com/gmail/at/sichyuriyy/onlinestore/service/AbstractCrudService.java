package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.SQLRuntimeException;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.TransactionFailedException;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.Transaction;
import org.apache.logging.log4j.LogManager;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Yuriy on 4/7/2017.
 */
public abstract class AbstractCrudService<T, PK extends Serializable> implements CrudService<T, PK> {

    public abstract Dao<T, PK> getBackingDao();

    @Override
    public PK create(T obj) {
        return getBackingDao().create(obj);
    }

    @Override
    public T findById(PK id) {
        return getBackingDao().findById(id);
    }

    @Override
    public void update(T obj) {
        getBackingDao().update(obj);
    }

    @Override
    public List<T> findAll() {
        return getBackingDao().findAll();
    }

    @Override
    public boolean delete(PK id) {
        try {
            LogManager.getLogger().info("try delete " + id);
            getBackingDao().delete(id);
        } catch(TransactionFailedException | SQLRuntimeException e) {
            return false;
        }
        return true;
    }
}
