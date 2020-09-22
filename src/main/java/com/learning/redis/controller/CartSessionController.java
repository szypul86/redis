package com.learning.redis.controller;

import com.learning.redis.model.Order;
import com.learning.redis.model.ShoppingCart;
import java.util.Collections;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;
import lombok.extern.java.Log;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

@Log
@Controller
@RequestMapping("/session")
@SessionAttributes("cart")
public class CartSessionController {

  private final AtomicLong ids = new AtomicLong();

  @ModelAttribute("cart")
  ShoppingCart cart() {
    log.info("creating new cart");
    return new ShoppingCart();
  }

  @GetMapping("/ordersys")
  String orders(@ModelAttribute("cart") ShoppingCart cart, Model model) {
    cart.addOrder(new Order(ids.incrementAndGet(), new Date(), Collections.emptyList()));
    model.addAttribute("orders", cart.getOrders());
    return "orders";
  }
}
