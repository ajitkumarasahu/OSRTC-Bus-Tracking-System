package com.osrtc.bustracking.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Utility class to manage database connections.
 * Provides a single method to get a MySQL database connection.
 */
public class DBConnection {

    // JDBC URL of the MySQL database (host, port, database name)
    private static final String URL = "jdbc:mysql://localhost:3306/osrtc_db";

    // Database username
    private static final String USER = "root";

    // Database password (empty in this case)
    private static final String PASSWORD = "";

    /**
     * Establishes and returns a database connection.
     * @return Connection object to interact with the database
     */
    public static Connection getConnection() {
        Connection conn = null; // Initialize connection object

        try {
            // Load the MySQL JDBC driver dynamically
            // This ensures the DriverManager knows about MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create a connection to the database using DriverManager
            conn = DriverManager.getConnection(URL, USER, PASSWORD);

            // Optional: Print success message
            System.out.println(" Database Connected Successfully!");
        } catch (Exception e) {
            // Catch any exceptions (driver not found, connection failure)
            e.printStackTrace(); // Print stack trace for debugging
        }

        // Return the Connection object (null if connection failed)
        return conn;
    }
}
