package org.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Press Shift twice to open the Search Everywhere dialog and type `show whitespaces`,
// then press Enter. You can now see whitespace characters in your code.
@SpringBootApplication
public class Main {
    public static void main(String[] args) {
//        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
//        String hashedPassword = passwordEncoder.encode("1234");
//        System.out.println(hashedPassword);

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String rawPassword = "your_raw_password";
        // Replace with your actual raw password
        String hashedPassword = passwordEncoder.encode(rawPassword);
        System.out.println("Hashed Password: " + hashedPassword);

        SpringApplication.run(Main.class, args);
    }
}