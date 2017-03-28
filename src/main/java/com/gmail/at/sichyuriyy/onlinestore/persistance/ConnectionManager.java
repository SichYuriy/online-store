package com.gmail.at.sichyuriyy.onlinestore.persistance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/28/2017.
 */
public class ConnectionManager {

    public static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);

    private static ConnectionManager ourInstance = new ConnectionManager();

    public static ConnectionManager getInstance() {
        return ourInstance;
    }

    private DataSource dataSource;

    private ConnectionManager() {
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Cannot get connection", e);
        }
        return null;
    }

}
