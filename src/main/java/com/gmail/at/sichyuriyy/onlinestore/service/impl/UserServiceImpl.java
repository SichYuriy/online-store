package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.entity.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.UserDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.Transaction;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.UserService;

/**
 * Created by Yuriy on 4/7/2017.
 */
public class UserServiceImpl extends AbstractCrudService<User, Long> implements UserService {

    private UserDao userDao;
    private ConnectionManager cm;

    public UserServiceImpl(UserDao userDao, ConnectionManager cm) {
        this.userDao = userDao;
        this.cm = cm;
    }

    @Override
    public Dao<User, Long> getBackingDao() {
        return userDao;
    }

    @Override
    public void create(User user) {
        Long id = userDao.create(user);
        user.setId(id);
    }

    @Override
    public void changeBlackListStatus(Long id, boolean blackList) {
        Transaction.tx(cm, () -> {
            User user = userDao.findById(id);
            user.setBlackList(blackList);
            userDao.update(user);
        });
    }
}
