package com.senne.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.senne.modal.HomeCategory;

public interface HomeCategoryRepository extends JpaRepository<HomeCategory, Long> {
    HomeCategory findByName(String name);

}
