package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseManager {
    static final String url = "jdbc:mysql://localhost/user";
    static final String dbUsername = "root";
     static final String dbPassword = "";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, dbUsername, dbPassword);
    }
}
