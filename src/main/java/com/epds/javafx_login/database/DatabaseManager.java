package com.epds.javafx_login.database;

import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;

public class DatabaseManager {

    private static final String DATABASE_URL = "jdbc:sqlite:tickets.db";
    private ConnectionSource connectionSource = null;

    public DatabaseManager() throws SQLException {
        this.connectionSource = new JdbcConnectionSource(DATABASE_URL);
    }

    public ConnectionSource getConnectionSource() {
        return connectionSource;
    }

    public void close() throws Exception {
        if (connectionSource != null) {
            connectionSource.close();
        }
    }

    // Creates tables of an entity class
    public void createTablesIfNotExists(Class<?>... classes) throws SQLException {
        for (Class<?> clazz : classes) {
            TableUtils.createTableIfNotExists(connectionSource, clazz);
        }
    }
}
