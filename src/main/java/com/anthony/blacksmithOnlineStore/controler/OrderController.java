package com.anthony.blacksmithOnlineStore.controler;

import com.anthony.blacksmithOnlineStore.controler.dto.Order.OrderResponseDto;
import com.anthony.blacksmithOnlineStore.entity.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
public class OrderController {

  @PostMapping
  public ResponseEntity<OrderResponseDto> create(
      @RequestBody OrderRequestDto dto, Authentication auth) {
    Order order = orderService.create(dto, auth);
    return ResponseEntity.status(HttpStatus.CREATED).body(OrderResponseDto.fromEntity(order));
  }
}
