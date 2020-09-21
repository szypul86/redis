package com.learning.redis.repository;

import com.learning.redis.model.User;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class UserRepositoryImpl implements UserRepository {


  private RedisTemplate redisTemplate;

  private HashOperations hashOperations;

  public UserRepositoryImpl(
      RedisTemplate redisTemplate) {

    this.hashOperations = redisTemplate.opsForHash();
    this.redisTemplate = redisTemplate;
  }

  @Override
  public void save(User user) {
    hashOperations.put("USER", user.getId(), user);
  }

  @Override
  public User findById(String id) {
    return  (User) hashOperations.get("USER", id);
  }

  @Override
  public Map<String, User> findAll() {
    return hashOperations.entries("USER");
  }

  @Override
  public void update(User user) {
    hashOperations.put("USER", user.getId(), user);
  }

  @Override
  public void delete(String id) {
    hashOperations.delete("USER", id);
  }
}
