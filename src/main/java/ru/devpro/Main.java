package ru.devpro;


import org.mapstruct.Mapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching

public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}