package com.learning.redis;

import com.learning.redis.model.User;
import lombok.extern.java.Log;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.geo.Circle;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.GeoResults;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands.DistanceUnit;
import org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.RedisTemplate;

@Log
@SpringBootApplication
public class RedisApplication {

  @Bean
  LettuceConnectionFactory connectionFactory() {
    return new LettuceConnectionFactory();
  }

  @Bean
  RedisTemplate<String, User> redisTemplate() {
    RedisTemplate<String, User> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(connectionFactory());
    return redisTemplate;
  }

  private ApplicationRunner titledRunner(String title, ApplicationRunner rr) {
    return args -> {
      log.info(title.toUpperCase() + ":");
      rr.run(args);
    };
  }

  @Bean
  ApplicationRunner geography(RedisTemplate<String, String> rt) {
    return titledRunner("geography", args -> {
      GeoOperations<String, String> geo = rt.opsForGeo();
      geo.add("Sicily", new Point(13.361389, 38.115556), "Arigento");
      geo.add("Sicily", new Point(15.087269, 37.502669), "Catania");
      geo.add("Sicily", new Point(13.583333, 37.316667), "Palermo");

      Circle circle = new Circle(new Point(13.583333, 37.316667),
          new Distance(100, DistanceUnit.KILOMETERS));

      GeoResults<GeoLocation<String>> geoResults = geo.radius("Sicily", circle);

      geoResults.getContent().forEach(
          c -> log.info(c.toString())
      );
    });
  }

  public static void main(String[] args) {
    SpringApplication.run(RedisApplication.class, args);
  }

}
