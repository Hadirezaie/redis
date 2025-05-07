package com.newcoders.redis.redis;

import java.time.Duration;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import reactor.core.publisher.Mono;

public class ReactiveRedisApplication {

    private static final Log LOG = LogFactory.getLog(ReactiveRedisApplication.class);

    public static void main(String[] args) {

        LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
        connectionFactory.afterPropertiesSet();

        ReactiveRedisTemplate<String, String> template = new ReactiveRedisTemplate<>(connectionFactory,
                RedisSerializationContext.string());

        Mono<Boolean> set = template.opsForValue().set("foo", "bar");
        set.block(Duration.ofSeconds(10));

        LOG.info("Value at foo:" + template.opsForValue().get("foo").block(Duration.ofSeconds(10)));

        connectionFactory.destroy();
    }
}