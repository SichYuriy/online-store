package com.gmail.at.sichyuriyy.onlinestore.service.impl;

import com.gmail.at.sichyuriyy.onlinestore.domain.User;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.Dao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.dao.UserDao;
import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.TransactionManager;
import com.gmail.at.sichyuriyy.onlinestore.service.AbstractCrudService;
import com.gmail.at.sichyuriyy.onlinestore.service.UserService;
import com.gmail.at.sichyuriyy.onlinestore.util.ServiceLocator;

/**
 * Created by Yuriy on 4/7/2017.
 */
public class UserServiceImpl extends AbstractCrudService<User, Long> implements UserService {

    private UserDao userDao;
    private TransactionManager transactionManager = ServiceLocator.INSTANCE.get(TransactionManager.class);

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
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
        transactionManager.tx(() -> {
            User user = userDao.findById(id);
            user.setBlackList(blackList);
            userDao.update(user);
        });
    }
}
