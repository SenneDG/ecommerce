package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.senne.modal.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    
    Seller findByEmail(String email);
}
