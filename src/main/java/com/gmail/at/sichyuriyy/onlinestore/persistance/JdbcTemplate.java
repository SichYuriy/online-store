package com.gmail.at.sichyuriyy.onlinestore.persistance;

import com.gmail.at.sichyuriyy.onlinestore.persistance.exception.SQLRuntimeException;
import com.gmail.at.sichyuriyy.onlinestore.persistance.mapper.RowMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yuriy on 3/27/2017.
 */
public class JdbcTemplate {

    private static final Logger LOGGER = LogManager.getLogger(JdbcTemplate.class);

    private ConnectionManager connectionManager;

    public JdbcTemplate(ConnectionManager connectionManager) {
        this.connectionManager = connectionManager;
    }

    private void query(String query, ResultSetFunction fn, Object... params) {
        Connection conn = connectionManager.getConnection();

        if(conn == null)
            return;

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            ResultSet rs = stmt.executeQuery();
            withRs(rs, fn);
        } catch (SQLException e) {
            LOGGER.warn("Cannot create prepared statement. Query: " + query, e);
            throw new SQLRuntimeException(e);
        } finally {
            tryClose(conn);
        }
    }

    public <T> List<T> queryObjects(String query, RowMapper<T> mapper, Object... params) {
        List<T> entities = new ArrayList<>();

        query(query, rs -> {
            while (rs.next()) {
                entities.add(mapper.map(rs));
            }
        }, params);

        return entities;
    }

    public <T> T queryObject(String query, RowMapper<T> mapper, Object... params) {
        Object[] r = new Object[]{null};
        query(query, (rs) -> {
            if (rs.next()) {
                r[0] = mapper.map(rs);
            }
        }, params);

        return (T) r[0];
    }

    public int update(String updQuery, Object... params) {
        Connection conn = connectionManager.getConnection();

        if (conn == null)
            return 0;

        try (PreparedStatement stmt = conn.prepareStatement(updQuery)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            return stmt.executeUpdate();
        } catch (SQLException e) {
            LOGGER.error("Cannot execute update query", e);
            throw new SQLRuntimeException(e);
        } finally {
            tryClose(conn);
        }
    }

    public Long insert(String updQuery, Object... params) {
        Connection conn = connectionManager.getConnection();

        if (conn == null)
            return null;

        try (PreparedStatement stmt
                     = conn.prepareStatement(updQuery, Statement.RETURN_GENERATED_KEYS)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            stmt.executeUpdate();
            ResultSet rs = stmt.getGeneratedKeys();

            if(rs.next()) {
                return rs.getLong(1);
            }

            return null;

        } catch (SQLException e) {
            LOGGER.error("Cannot insert values into DB", e);
            throw new SQLRuntimeException(e);
        } finally {
            tryClose(conn);
        }
    }

    private void withRs(ResultSet rs, ResultSetFunction fn) {
        try {
            fn.apply(rs);
        } catch (Exception e) {
            LOGGER.info("ResultSetFunctions has thrown an exception", e);
            throw new SQLRuntimeException(e);
        } finally {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.error("Cannot tryClose ResultSet", e);
                throw new SQLRuntimeException(e);
            }
        }
    }

    private void tryClose(Connection connection) {
        try {
            connection.close();
        } catch (SQLException e) {
            LOGGER.error("Cannot close jdbc connection", e);
            throw new SQLRuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ResultSetFunction {
        void apply(ResultSet resultSet) throws SQLException;
    }

}
