package com.learning.redis.controller;

import com.learning.redis.model.User;
import com.learning.redis.repository.UserRepository;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rest/user")
public class UserResource {

  @Autowired
  private final UserRepository userRepository;

  public UserResource(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @GetMapping("/add/{id}/{name}")
  public User add(@PathVariable("id") String id, @PathVariable("name") String name) {
    userRepository.save(new User(id, name, 20000L));
    return userRepository.findById(id);
  }

  @GetMapping("/update/{id}/{name}")
  public User update(@PathVariable("id") String id, @PathVariable("name") String name) {
    userRepository.save(new User(id, name, 50000L));
    return userRepository.findById(id);
  }

  @GetMapping("/all/")
  public Map<String, User> findAll() {

    return userRepository.findAll();
  }


}
