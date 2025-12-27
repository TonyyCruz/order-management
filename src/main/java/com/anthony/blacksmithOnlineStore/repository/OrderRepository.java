package com.anthony.blacksmithOnlineStore.repository;

import com.anthony.blacksmithOnlineStore.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
