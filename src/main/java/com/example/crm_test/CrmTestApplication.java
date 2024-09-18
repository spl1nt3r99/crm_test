package com.example.crm_test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class CrmTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrmTestApplication.class, args);
    }
}
