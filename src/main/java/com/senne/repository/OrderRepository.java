package com.senne.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import com.senne.modal.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findByUserId(Long userId);

    List<Order> findBySellerId(Long sellerId);
}
