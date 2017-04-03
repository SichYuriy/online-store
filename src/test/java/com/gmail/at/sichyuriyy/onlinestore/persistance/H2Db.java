package com.gmail.at.sichyuriyy.onlinestore.persistance;

import com.gmail.at.sichyuriyy.onlinestore.persistance.transaction.DataSourceTxProxy;
import com.gmail.at.sichyuriyy.onlinestore.persistance.util.ScriptRunner;
import com.gmail.at.sichyuriyy.onlinestore.util.ResourcesUtil;
import org.h2.jdbcx.JdbcDataSource;

/**
 * Created by Yuriy on 3/31/2017.
 */
public class H2Db {

    public static ConnectionManager init() {
        JdbcDataSource ds = getH2DS();
        return ConnectionManager.fromDataSource(ds);
    }

    public static ConnectionManager initWithTx() {
        JdbcDataSource ds = getH2DS();
        DataSourceTxProxy dataSourceTxProxy = new DataSourceTxProxy(ds);
        return ConnectionManager.fromDataSource(dataSourceTxProxy);
    }

    private static JdbcDataSource getH2DS() {
        JdbcDataSource ds = new JdbcDataSource();
        ds.setUrl("jdbc:h2:mem:test;Mode=MYSQL;DB_CLOSE_DELAY=-1");
        ds.setUser("sa");
        ds.setPassword("sa");
        return ds;
    }
}
