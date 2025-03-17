
package com.helper;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBHelper {
    private static final String JDBC_URL = "jdbc:derby:AirlineDB;create=true";
    
    static {
        // Set the Derby system home using a full path.
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            System.setProperty("derby.language.sequence.preallocator", "1");
        } catch (ClassNotFoundException e) {
            System.err.println("Derby Embedded Driver not found.");
            e.printStackTrace();
        }
    }
    
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL);
    }
}
