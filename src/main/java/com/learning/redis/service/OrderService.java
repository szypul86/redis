package com.learning.redis.service;

import com.learning.redis.model.Order;
import java.util.Collections;
import java.util.Date;
import lombok.extern.java.Log;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Log
@Service
public class OrderService {

  @Cacheable("order-by-id")
  public Order orderById(Long id) {
    try {
      Thread.sleep(1000 * 10);
    } catch (Throwable e) {
      throw new RuntimeException(e);
    }
    return new Order(id, new Date(), Collections.emptyList());
  }

}
