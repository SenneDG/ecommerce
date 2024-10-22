package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.PaymentOrder;

public interface PaymentOrderRepository extends JpaRepository<PaymentOrder, Long> {
    
    PaymentOrder findByPaymentLinkId(String paymentId);
}
