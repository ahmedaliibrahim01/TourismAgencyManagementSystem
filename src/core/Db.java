package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Database utility class for managing database connections using Singleton Design Pattern.
 */
public class Db {

    // Singleton Design Pattern
    private static Db instance = null;
    private Connection connection = null;

    // Database connection parameters
    private static final String DB_URL = "jdbc:postgresql://localhost:5432/tourismagencysystem";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASS = "postgres";

    /**
     * Private constructor to prevent instantiation from outside.
     * Establishes a database connection using the specified parameters.
     */
    private Db() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASS);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Returns the established database connection.
     * @return The database connection.
     */
    public Connection getConnection() {
        return connection;
    }

    /**
     * Provides a singleton instance of the database connection.
     * @return The singleton instance of the database connection.
     */
    public static Connection getInstance() {
        try {
            if (instance == null || instance.getConnection().isClosed()) {
                instance = new Db();
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return instance.getConnection();
    }
}
