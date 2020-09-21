package com.learning.redis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Reference;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@RedisHash("orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order implements Serializable {

  @Id
  private Long id;

  @Indexed
  private Date when;

  @Reference
  private List<LineItem> lineItems;

}
