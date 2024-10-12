package com.senne.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.domain.AccountStatus;
import com.senne.modal.Seller;

public interface SellerRepository extends JpaRepository<Seller, Long> {
    
    Seller findByEmail(String email);
    List<Seller> findByAccountStatus(AccountStatus status);
}
