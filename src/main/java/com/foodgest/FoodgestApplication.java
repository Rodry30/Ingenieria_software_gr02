package com.foodgest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;

@SpringBootApplication(exclude = {UserDetailsServiceAutoConfiguration.class})
public class FoodgestApplication {
    public static void main(String[] args) {
        // Ensure database exists and run init script before Spring starts
        try {
            com.foodgest.db.DatabaseInitializer.ensureDatabaseAndInit();
        } catch (Exception e) {
            System.err.println("[DatabaseInitializer] warning: " + e.getMessage());
        }

        SpringApplication.run(FoodgestApplication.class, args);
    }
}
