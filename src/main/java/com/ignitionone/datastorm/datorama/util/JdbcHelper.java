package com.ignitionone.datastorm.datorama.util;
import com.amazonaws.services.devicefarm.model.ArgumentException;

import java.sql.*;
import java.util.Properties;

/**
 * Created by aaron.schwan on 12/20/2016.
 */
public class JdbcHelper {
    private static String SQL_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
    private String connectionUrl;
    private Connection connection;


    public JdbcHelper(String connectionUrl) {
        if (connectionUrl == null || connectionUrl.length() == 0) {
            throw new ArgumentException("connection Url is null or empty");
        }
        this.connectionUrl = connectionUrl;
        try {
            this.connection = DriverManager.getConnection(connectionUrl);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public JdbcHelper(String connectionUrl, String user, String password) {
        Properties connectionProperties = new Properties();

    }

}
