package com.anthony.blacksmithOnlineStore.service;

import com.anthony.blacksmithOnlineStore.controler.dto.rating.RatingRequestDto;
import com.anthony.blacksmithOnlineStore.entity.OrderItem;
import com.anthony.blacksmithOnlineStore.entity.Rating;
import com.anthony.blacksmithOnlineStore.exceptions.ForbiddenOperationException;
import com.anthony.blacksmithOnlineStore.exceptions.OrderItemNotFoundException;
import com.anthony.blacksmithOnlineStore.repository.OrderItemRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RatingService {
  private final OrderItemRepository orderItemRepository;
  private final BlacksmithService blacksmithService;
  private final ItemService itemService;
  private final UserService userService;

  @Transactional
  public void ratePurchase(RatingRequestDto dto, Authentication auth) {
    OrderItem orderItem = orderItemRepository.findById(dto.orderItemId())
        .orElseThrow(() -> new OrderItemNotFoundException(dto.orderItemId()));
    if (!orderItem.getUserId().equals(auth.getDetails())) {
      throw new ForbiddenOperationException("Only hwo purchased the item can rate it.");
    }
    if (orderItem.isReviewed()) {
      throw new ForbiddenOperationException("This item has already been rated.");
    }
    blacksmithService.addRating(orderItem.getBlacksmithId(), dto.rating());
    itemService.addRating(orderItem.getItemId(), dto.rating());
    Rating rating = RatingRequestDto.toEntity(dto);
    rating.setOrderItem(orderItem);
    rating.setUser(userService.getUserReferenceFromAuth(auth));
  }

  acabei de adicionar a logica de rating
}
