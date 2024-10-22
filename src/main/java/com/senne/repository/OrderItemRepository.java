package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    
}
