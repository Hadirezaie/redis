package com.newcoders.redis.redis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class AdvanceRedis {
    public static void main(String[] args) {
        SpringApplication.run(AdvanceRedis.class, args);

    }
}
