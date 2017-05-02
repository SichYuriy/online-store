package com.gmail.at.sichyuriyy.onlinestore.persistance.transaction;

import com.gmail.at.sichyuriyy.onlinestore.persistance.ConnectionManager;
import com.gmail.at.sichyuriyy.onlinestore.persistance.H2Db;
import com.gmail.at.sichyuriyy.onlinestore.persistance.JdbcTemplate;
import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.TransactionFailedException;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Yuriy on 5/2/2017.
 */
public class TransactionTest {
    private static final String SQL_SELECT_ALL = "SELECT * FROM test";

    private ConnectionManager connectionManager;
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() throws Exception {
        connectionManager = H2Db.initWithTx();
        jdbcTemplate = new JdbcTemplate(connectionManager);
        ScriptRunner scriptRunner = new ScriptRunner(connectionManager);
        scriptRunner.executeScript(ResourcesUtil.getResourceFile("test_tx.sql"));
    }

    @Test
    public void testTxImplicitRollbackSecondOp() {
        try {
            Transaction.tx(connectionManager, () -> {
                JdbcTemplate txTemplate = new JdbcTemplate(connectionManager);

                Long id1 = txTemplate.insert("INSERT INTO test(col) VALUES(?)", "alpha");
                assertEquals(Long.valueOf(4), id1);

                txTemplate.insert("INSERT INTO test(col, id) VALUES(?, ?)", null, 1);
            });
        } catch (Exception e) {}

        List<String> cols = jdbcTemplate.queryObjects(SQL_SELECT_ALL,
                rs -> rs.getString("col"));
        assertEquals(Arrays.asList("x", "y", "z"), cols);
    }

    @Test
    public void testTxImplicitRollbackFirstOp() {
        try {
            Transaction.tx(connectionManager, () -> {
                JdbcTemplate txTemplate = new JdbcTemplate(connectionManager);

                txTemplate.insert("INSERT INTO test(col, id) VALUES(?, ?)", null, 1);
                txTemplate.insert("INSERT INTO test(col) VALUES(?)", "alpha");
            });
        } catch (Exception e) {}

        List<String> cols = jdbcTemplate.queryObjects(SQL_SELECT_ALL, rs -> rs.getString("col")
        );
        assertEquals(Arrays.asList("x", "y", "z"), cols);
    }

    @Test
    public void testTxImplicitRollbackFirstOpWithoutExplicitJdbcTemplateTransaction() {
        try {
            Transaction.tx(connectionManager, () -> {
                JdbcTemplate txTemplate = new JdbcTemplate(connectionManager);

                Long id1 = txTemplate.insert("INSERT INTO test(col, id) VALUES(?, ?)", null, 1);
                Long id2 = txTemplate.insert("INSERT INTO test(col) VALUES(?)", "alpha");
            });
        } catch (Exception e) {}

        List<String> cols = jdbcTemplate.queryObjects(SQL_SELECT_ALL,
                rs -> rs.getString("col"));
        assertEquals(Arrays.asList("x", "y", "z"), cols);
    }

    @Test
    public void testTxExplicitRollback() {
        try {
            Transaction.tx(connectionManager, () -> {
                JdbcTemplate txTemplate = new JdbcTemplate(connectionManager);

                txTemplate.insert("INSERT INTO test(col) VALUES(?)", "alpha");

                throw new TransactionFailedException();
            });
        } catch (Exception e) {}

        List<String> cols = jdbcTemplate.queryObjects(SQL_SELECT_ALL,
                rs -> rs.getString("col"));
        assertEquals(Arrays.asList("x", "y", "z"), cols);
    }

    @Test
    public void testNestedTransactionOk() {
        Transaction.tx(connectionManager, () -> {
            JdbcTemplate txTemplate = new JdbcTemplate(connectionManager);

            txTemplate.insert("INSERT INTO test(col) VALUES(?)", "alpha");

            Transaction.tx(connectionManager, () -> {
                txTemplate.insert("INSERT INTO test(col) VALUES(?)", "beta");
            });
        });

        List<String> cols = jdbcTemplate.queryObjects(SQL_SELECT_ALL,
                rs -> rs.getString("col"));
        assertEquals(Arrays.asList("x", "y", "z","alpha", "beta"), cols);
    }

    @Test
    public void testNestedTransactionWhenNestedFails() {
        connectionManager.clean();
        try {
            Transaction.tx(connectionManager, () -> {
                JdbcTemplate txTemplate = new JdbcTemplate(connectionManager);

                txTemplate.insert("INSERT INTO test(col) VALUES(?)", "alpha");

                Transaction.tx(connectionManager, () -> {
                    txTemplate.insert("INSERT INTO test(col) VALUES(?, ?)", "beta");
                });
            });
        } catch (Exception e) {}

        List<String> cols = jdbcTemplate.queryObjects(SQL_SELECT_ALL,
                rs -> rs.getString("col"));
        assertEquals(Arrays.asList("x", "y", "z"), cols);
    }

    @Test
    public void testNestedTransactionWhenOuterFails() {
        try {
            Transaction.tx(connectionManager, () -> {
                JdbcTemplate txTemplate = new JdbcTemplate(connectionManager);
                txTemplate.insert("INSERT INTO test(col) VALUES(?, ?)", "alpha");

                Transaction.tx(connectionManager, () -> {
                    txTemplate.insert("INSERT INTO test(col) VALUES(?)", "beta");
                });
            });
        } catch (Exception e) {}

        List<String> cols = jdbcTemplate.queryObjects(SQL_SELECT_ALL,
                rs -> rs.getString("col"));
        assertEquals(Arrays.asList("x", "y", "z"), cols);
    }
}