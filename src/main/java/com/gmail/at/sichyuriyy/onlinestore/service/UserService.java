package com.gmail.at.sichyuriyy.onlinestore.service;

import com.gmail.at.sichyuriyy.onlinestore.entity.User;

/**
 * Created by Yuriy on 4/7/2017.
 */
public interface UserService extends CrudService<User, Long> {

    void changeBlackListStatus(Long id, boolean blackList);
}
