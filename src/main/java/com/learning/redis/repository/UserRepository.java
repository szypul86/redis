package com.learning.redis.repository;

import com.learning.redis.model.User;
import java.util.Map;

public interface UserRepository {

  void save(User user);

  User findById(String id);

  Map<String, User> findAll();

  void update(User user);

  void delete(String id);
}
