package ma.chakiri.gestionconsultation.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/DBCABINET";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // No password by default on Homebrew install
    private static Connection connection;

    static {
        try {
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to the database!");
        }
    }

    public static Connection getConnection() {
        return connection;
    }
}