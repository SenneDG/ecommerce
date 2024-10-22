package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.User;
import com.senne.modal.WishList;

public interface WishlistRepository extends JpaRepository<WishList, Long>{
    WishList findByUser(User user);
}
