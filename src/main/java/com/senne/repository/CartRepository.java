package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.Cart;

public interface CartRepository extends JpaRepository<Cart, Long> {

}
