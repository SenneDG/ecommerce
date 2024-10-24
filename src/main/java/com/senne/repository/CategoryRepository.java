package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    
    Category findByCategoryId(String categoryId);
}
