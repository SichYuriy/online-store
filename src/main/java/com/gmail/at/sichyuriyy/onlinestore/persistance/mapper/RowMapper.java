package com.gmail.at.sichyuriyy.onlinestore.persistance.mapper;

import java.sql.ResultSet;

/**
 * Created by Yuriy on 3/28/2017.
 */
@FunctionalInterface
public interface RowMapper<T> {

    T map(ResultSet resultSet);
}
