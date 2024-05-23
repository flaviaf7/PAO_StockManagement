package database;

import java.sql.*;

public class JdbcConnection {
    private static Connection connection = null;
    private final static String jdbcURL = "jdbc:mysql://localhost:3306/stockmanagement";
    private static JdbcConnection jdbcConnection;

    public static JdbcConnection getJdbcSettings() {
        if (jdbcConnection == null) {
            jdbcConnection = new JdbcConnection();
        }
        return jdbcConnection;
    }

    private static void registerDriver() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        }
        catch (Exception e) {
            System.out.println("driver registration failed\n");
            e.printStackTrace();
        }
    }

    private static void createConnection() {
        try {
            connection = DriverManager.getConnection(jdbcURL, "root", "Mysqlflavia123");
        }
        catch (Exception e) {
            System.out.println("ERROR :: Setup createConnection :: could not create connection\n");
            e.printStackTrace();
        }
    }

    static {
        registerDriver();
        createConnection();
    }
    public static Connection getConnection() {
        return connection;
    }
}