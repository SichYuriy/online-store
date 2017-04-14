package com.gmail.at.sichyuriyy.onlinestore.persistance.transaction;

import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.SQLRuntimeException;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.TransactionFailedException;
import org.apache.logging.log4j.LogManager;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by Yuriy on 3/28/2017.
 */

@FunctionalInterface
public interface Transaction {

    void process();

    static void tx(ConnectionManager cm, Transaction transaction, int transactionIsolationLevel) {
        Connection conn = cm.getConnection();
        if (conn == null) {
            throw new TransactionFailedException();//TODO:
        }

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
        } catch (RuntimeException e) {
            LogManager.getLogger().error("transaction failed", e);
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
        tx(cm, transaction, Connection.TRANSACTION_READ_COMMITTED);
    }
}
