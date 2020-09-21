package com.learning.redis;

import com.learning.redis.model.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
public class RedisApplication {

  @Bean
  LettuceConnectionFactory connectionFactory() {
    return new LettuceConnectionFactory();
  }

  @Bean
  RedisTemplate<String, User> redisTemplate() {
    RedisTemplate<String,User> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory());
    return  redisTemplate;
  }

  public static void main(String[] args) {
    SpringApplication.run(RedisApplication.class, args);
  }

}
