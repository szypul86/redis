package com.learning.redis;

import com.learning.redis.model.LineItem;
import com.learning.redis.model.Order;
import com.learning.redis.model.User;
import com.learning.redis.repository.LineItemRepository;
import com.learning.redis.repository.OrderRepository;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Random;
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
import org.springframework.data.repository.CrudRepository;

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
  ApplicationRunner repositories(OrderRepository orderRepository, LineItemRepository lineItemRepository) {
    return titledRunner("repositories", args -> {
      Long orderId = generateId();
      List<LineItem> itemList = Arrays.asList(new LineItem(orderId, generateId(), "plunger"),
          new LineItem(orderId, generateId(), "soup"),
          new LineItem(orderId, generateId(), "broccoli"));
      itemList.stream()
          .map(lineItemRepository::save)
          .forEach((li -> log.info(li.toString())));

      Order order = new Order(orderId, new Date(), itemList);
      orderRepository.save(order);
      Collection<Order> found = orderRepository.findByWhen(order.getWhen());
  found.forEach(o -> log.info("found: " + o.toString()));
    });
  }

  private Long generateId() {
    long tmp = new Random().nextLong();
    return Math.max(tmp, tmp * -1);
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
