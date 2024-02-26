package org.mb.configuration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mb.configuration.PropertiesLoader.*;

public class DatabaseConnectionConfiguration {

    public static synchronized void initialize() {
        try {
            Class.forName("org.h2.Driver");
            initDatabase();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not load H2 JDBC driver", e);
        }
    }

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(getJdbcUrl(), getUsername(), getPassword());
    }

    private static void initDatabase() {
        String sql = """
                CREATE TABLE IF NOT EXISTS SUSERS (
                USER_ID INT PRIMARY KEY,
                USER_GUID VARCHAR(255) NOT NULL,
                USER_NAME VARCHAR(255) NOT NULL
                )""";

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            throw new RuntimeException("Error during initializing the database", e);
        }
    }
}
