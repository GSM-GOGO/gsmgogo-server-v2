package com.example.gsmgogo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.example")
public class GsmgogoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(GsmgogoApiApplication.class, args);
    }

}
