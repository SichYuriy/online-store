package com.gmail.at.sichyuriyy.onlinestore.persistance.dao;

import com.gmail.at.sichyuriyy.onlinestore.entity.Role;
import com.gmail.at.sichyuriyy.onlinestore.entity.User;

import java.util.List;

/**
 * Created by Yuriy on 3/28/2017.
 */
public interface UserDao extends Dao<User, Long> {

    void addRoles(Long userId, List<Role> roles);

    void addRole(Long userId, Role role);

    void deleteRoles(Long userId);

    void updateRoles(Long userId, List<Role> roles);

    List<Role> findRoles(Long userId);

    User findByName(String username);

    List<User> findPart(int limit, int offset);

    List<User> findByBlackList(Boolean blackList, int limit, int offset);
}
