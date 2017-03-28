package com.gmail.at.sichyuriyy.onlinestore.persistance.Transaction;

import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.SQLRuntimeException;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/28/2017.
 */

@FunctionalInterface
public interface Transaction {

    void process();

    static void tx(Transaction transaction, int transactionIsolationLevel) {
        ConnectionManager cm = ConnectionManager.getInstance();
        Connection conn = cm.getConnection();

        boolean autoCommit;
        try {
            autoCommit = conn.getAutoCommit();
            conn.setAutoCommit(false);

            if(conn.getTransactionIsolation() != transactionIsolationLevel) {
                conn.setTransactionIsolation(transactionIsolationLevel);
            }

            transaction.process();
            conn.setAutoCommit(autoCommit);
        } catch (SQLException e) {
            try {
                conn.rollback();
            } catch (SQLException e1) {
                LogManager.getLogger().error("Cannot rollback transaction", e1);
            }

            throw new SQLRuntimeException(e);
        } catch (Exception e) {
            LogManager.getLogger().error("Transaction failed", e);
            throw e;
        } finally {
            try {
                conn.close();
            } catch (SQLException e) {
                LogManager.getLogger().error("Cannot close connection", e);
            }
        }
    }

    static void tx(ConnectionManager cm, Transaction transaction) {
        tx(transaction, Connection.TRANSACTION_READ_COMMITTED);
    }
}
