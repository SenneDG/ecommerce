package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.Cart;
import com.senne.modal.CartItem;
import com.senne.modal.Product;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    CartItem findByCartAndProductAndSize(Cart cart, Product product, String size);
}
