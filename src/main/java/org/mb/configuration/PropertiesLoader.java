package org.mb.configuration;

import java.io.InputStream;
import java.util.Properties;

public class PropertiesLoader {

    private static String jdbcUrl;
    private static String username;
    private static String password;

    public static void loadDatabaseProperties() {
        try (InputStream input = PropertiesLoader.class.getClassLoader().getResourceAsStream("application.properties")) {
            Properties prop = new Properties();

            if (input == null) {
                throw new RuntimeException("Unable to find application.properties");
            }
            prop.load(input);

            jdbcUrl = prop.getProperty("jdbc.url");
            username = prop.getProperty("jdbc.username");
            password = prop.getProperty("jdbc.password");

            System.out.println("Loaded JDBC URL: " + jdbcUrl);
            System.out.println("Loaded JDBC Username: " + username);

        } catch (Exception ex) {
            throw new RuntimeException("Error during loading database configuration from application.properties", ex);
        }
    }

    public static String getJdbcUrl() {
        return jdbcUrl;
    }

    public static String getUsername() {
        return username;
    }

    public static String getPassword() {
        return password;
    }
}
