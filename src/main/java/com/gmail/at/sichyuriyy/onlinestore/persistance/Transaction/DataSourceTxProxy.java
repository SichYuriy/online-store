package com.gmail.at.sichyuriyy.onlinestore.persistance.Transaction;

import org.apache.logging.log4j.LogManager;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class DataSourceTxProxy implements DataSource {


    private DataSource ds;

    private final ThreadLocal<ConnectionTxProxy> connThreadLocal = new ThreadLocal<>();

    public DataSourceTxProxy(DataSource ds) {
        this.ds = ds;
    }

    void onFree() throws SQLException {
        ConnectionTxProxy conn = connThreadLocal.get();
        conn.getConnection().close();
        connThreadLocal.remove();
    }

    @Override
    public Connection getConnection() throws SQLException {
        if (connThreadLocal.get() != null) {
            ConnectionTxProxy conn = connThreadLocal.get();
            conn.borrow();
            return conn;
        }

        ConnectionTxProxy conn = new ConnectionTxProxy(ds.getConnection(), this);
        conn.borrow();
        connThreadLocal.set(conn);
        return conn;
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return ds.getLoginTimeout();
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return ds.getLogWriter();
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return ds.getParentLogger();
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        ds.setLoginTimeout(seconds);
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        ds.setLogWriter(out);
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return ds.isWrapperFor(iface);
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return ds.unwrap(iface);
    }

    public void clean() {
        ConnectionTxProxy conn = connThreadLocal.get();

        if (conn != null) {
            try {
                conn.getConnection().close();
            } catch (SQLException e) {
                LogManager.getLogger(DataSourceTxProxy.class).error("Cannot close connection", e);
            } finally {
                connThreadLocal.remove();
            }
        }
    }
}
