package com.gmail.at.sichyuriyy.onlinestore.persistance;

import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.DataSourceTxProxy;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            LOGGER.error("Cannot get connection", e);
        }
        if (connection == null) {
            LOGGER.error("Cannot get connection");
        }
        return connection;
    }

    public static ConnectionManager fromDataSource(DataSource dataSource) {
        ConnectionManager cm = new ConnectionManager(dataSource);
        return cm;
    }

    public static ConnectionManager fromJndi(String name) {
        try {
            Context initContext = new InitialContext();
            Context envContext  = (Context)initContext.lookup("java:/comp/env");
            DataSource ds = (DataSource)envContext.lookup(name);
            DataSource txDs = new DataSourceTxProxy(ds);

            ConnectionManager connManager = new ConnectionManager(txDs);
            return connManager;
        } catch (NamingException e) {
            LOGGER.error("Cannot create InitialContext", e);
            return null;
        }
    }

    public void clean() {
        if (dataSource instanceof DataSourceTxProxy) {
            DataSourceTxProxy txDs = (DataSourceTxProxy) dataSource;
            txDs.clean();
        }
    }


}
