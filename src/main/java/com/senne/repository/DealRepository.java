package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.Deal;

public interface DealRepository extends JpaRepository<Deal, Long> {

}
