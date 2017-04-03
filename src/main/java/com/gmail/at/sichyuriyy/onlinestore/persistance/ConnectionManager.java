package com.gmail.at.sichyuriyy.onlinestore.persistance;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sql.DataSource;
import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by Yuriy on 3/28/2017.
 */
public class ConnectionManager {

    private static final Logger LOGGER = LogManager.getLogger(ConnectionManager.class);

    private DataSource dataSource;

    private ConnectionManager(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Cannot get connection", e);
        }
        return null;
    }

    public static ConnectionManager fromDataSource(DataSource dataSource) {
        ConnectionManager cm = new ConnectionManager(dataSource);
        return cm;
    }


}
