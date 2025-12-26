package com.anthony.blacksmithOnlineStore.service;

import com.anthony.blacksmithOnlineStore.repository.OrderItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OrderItemService {
  private final OrderItemRepository orderItemRepository;

}
