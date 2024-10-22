package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.SellerReport;

public interface SellerReportRepository extends JpaRepository<SellerReport, Long> {
    SellerReport findBySellerId(Long sellerId);
}
