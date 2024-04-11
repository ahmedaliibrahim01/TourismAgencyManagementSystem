package core;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Db {

    // Singleton Design Pattern
    private static Db instance = null;
    private Connection connection = null;

    private static final String DB_URL = "jdbc:postgresql://localhost:5432/tourismagencysystem";
    private static final String DB_USERNAME = "postgres";
    private static final String DB_PASS = "ahmed0615592573";

    private Db() {
        try {
            this.connection = DriverManager.getConnection(DB_URL, DB_USERNAME, DB_PASS);
            System.out.println("Connection is successful");
        } catch (SQLException e) {
            System.out.println("Connection is not successful");
            System.out.println(e.getMessage());
        }
    }

    public Connection getConnection() {
        return connection;
    }

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
