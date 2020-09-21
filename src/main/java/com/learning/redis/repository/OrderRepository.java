package com.learning.redis.repository;

import com.learning.redis.model.Order;
import java.util.Collection;
import java.util.Date;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<Order,Long> {
  Collection<Order> findByWhen(Date when);
}
