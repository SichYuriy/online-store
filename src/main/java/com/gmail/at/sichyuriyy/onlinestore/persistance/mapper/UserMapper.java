package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import com.gmail.at.sichyuriyy.onlinestore.domain.User;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/29/2017.
 */
public class UserMapper implements RowMapper<User> {
    @Override
    public User map(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getLong("id"));
        user.setLogin(rs.getString("login"));
        user.setPassword(rs.getString("password"));
        user.setBlackList(rs.getBoolean("black_list"));
        return user;
    }
}
