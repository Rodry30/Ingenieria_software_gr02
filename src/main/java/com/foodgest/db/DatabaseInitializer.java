package com.foodgest.db;

import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import java.net.URI;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DatabaseInitializer {

    public static void ensureDatabaseAndInit() {
        // Prefer environment variables; fall back to application.properties on classpath
        String dbUrl = System.getenv("DB_URL");
        String dbUser = System.getenv("DB_USER");
        String dbPassword = System.getenv("DB_PASSWORD");

        if (dbUrl == null || dbUser == null || dbPassword == null) {
            try {
                java.util.Properties props = new java.util.Properties();
                org.springframework.core.io.ClassPathResource appProps = new org.springframework.core.io.ClassPathResource("application.properties");
                if (appProps.exists()) {
                    try (java.io.InputStream in = appProps.getInputStream()) {
                        props.load(in);
                    }
                }

                if (dbUrl == null) dbUrl = props.getProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/foodgest");
                if (dbUser == null) dbUser = props.getProperty("spring.datasource.username", "postgres");
                if (dbPassword == null) dbPassword = props.getProperty("spring.datasource.password", "123456");
            } catch (Exception e) {
                System.err.println("[DatabaseInitializer] could not read application.properties: " + e.getMessage());
            }
        }

        if (dbUrl == null) dbUrl = "jdbc:postgresql://localhost:5432/foodgest";
        if (dbUser == null) dbUser = "postgres";
        if (dbPassword == null) dbPassword = "123456";

        String dbName = parseDbName(dbUrl);
        if (dbName == null) {
            System.err.println("[DatabaseInitializer] could not parse DB name from URL: " + dbUrl);
            return;
        }

        String adminUrl = dbUrl.replace("/" + dbName, "/postgres");

        try (Connection adminConn = DriverManager.getConnection(adminUrl, dbUser, dbPassword)) {
            // check if database exists
            try (PreparedStatement ps = adminConn.prepareStatement("SELECT 1 FROM pg_database WHERE datname = ?")) {
                ps.setString(1, dbName);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        System.out.println("[DatabaseInitializer] creating database: " + dbName);
                        try (PreparedStatement create = adminConn.prepareStatement("CREATE DATABASE \"" + dbName + "\"") ) {
                            create.execute();
                        }
                    } else {
                        System.out.println("[DatabaseInitializer] database exists: " + dbName);
                    }
                }
            }

            // now run init script against the target DB
            DriverManagerDataSource ds = new DriverManagerDataSource();
            ds.setUrl(dbUrl);
            ds.setUsername(dbUser);
            ds.setPassword(dbPassword);

            ClassPathResource resource = new ClassPathResource("init_db.sql");
            if (!resource.exists()) {
                System.out.println("[DatabaseInitializer] init_db.sql not found in classpath, skipping script execution.");
                return;
            }

            System.out.println("[DatabaseInitializer] executing init_db.sql on " + dbName + " (may require superuser privileges to create extensions)");
            ResourceDatabasePopulator populator = new ResourceDatabasePopulator(resource);
            // allow continuing even if some statements (e.g. CREATE EXTENSION) fail due to permissions
            populator.setContinueOnError(true);
            populator.execute(ds);

        } catch (Exception e) {
            System.err.println("[DatabaseInitializer] failed: " + e.getMessage());
        }
    }

    private static String parseDbName(String jdbcUrl) {
        try {
            // handle typical jdbc:postgresql://host:port/dbname?params
            String prefix = "jdbc:postgresql://";
            if (!jdbcUrl.startsWith(prefix)) return null;
            String without = jdbcUrl.substring(prefix.length());
            int slash = without.indexOf('/');
            if (slash < 0) return null;
            String after = without.substring(slash + 1);
            int qm = after.indexOf('?');
            if (qm >= 0) after = after.substring(0, qm);
            return after;
        } catch (Exception e) {
            return null;
        }
    }
}
