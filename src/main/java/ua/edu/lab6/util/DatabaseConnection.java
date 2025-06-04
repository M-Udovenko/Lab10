package ua.edu.lab6.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public static Connection getSqliteConnection() {
        try {
            return DriverManager.getConnection("jdbc:sqlite:cats.db");
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to SQLite database", e);
        }
    }

    public static Connection getPostgresConnection() {
        String url = "jdbc:postgresql://localhost:5432/cats_db";
        String username = "postgres";
        String password = "password";

        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException("Cannot connect to PostgreSQL database", e);
        }
    }
}