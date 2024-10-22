package com.senne.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    
    List<Review> findByProductId(Long productId);
}
