package com.newcoders.redis.redis;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// @SpringBootApplication
public class RedisApplication {

	private static final Log LOG = LogFactory.getLog(RedisApplication.class);

	public static void main(String[] args) {
		// SpringApplication.run(RedisApplication.class, args);

		LettuceConnectionFactory connectionFactory = new LettuceConnectionFactory();
		connectionFactory.afterPropertiesSet();

		RedisTemplate<String, String> template = new RedisTemplate<>();
		template.setConnectionFactory(connectionFactory);
		template.setDefaultSerializer(StringRedisSerializer.UTF_8);
		template.afterPropertiesSet();

		template.opsForValue().set("key", "value");

		LOG.info("Value for key: " + template.opsForValue().get("foo"));

		connectionFactory.destroy();
	}

}
