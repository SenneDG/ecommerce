package com.senne.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senne.modal.HomeCategory;
import com.senne.repository.HomeCategoryRepository;
import com.senne.service.HomeCategoryService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeCategoryServiceImpl implements HomeCategoryService {

    private final HomeCategoryRepository homeCategoryRepository;
    
    @Override
    public HomeCategory createHomeCategory(HomeCategory homeCategory) {
        return homeCategoryRepository.save(homeCategory);
    }

    @Override
    public List<HomeCategory> createHomeCategories(List<HomeCategory> homeCategories) {
        if(homeCategoryRepository.findAll().isEmpty()) {
            return homeCategoryRepository.saveAll(homeCategories);
        }

        return homeCategoryRepository.findAll();
    }

    @Override
    public HomeCategory updateHomeCategory(HomeCategory homeCategory, Long id) throws Exception {
        HomeCategory existingHomeCategory = homeCategoryRepository.findById(id).orElseThrow(() -> 
            new Exception("Home Category not found"));

        if(homeCategory.getImage() != null) {
            existingHomeCategory.setImage(homeCategory.getImage());
        }

        if(homeCategory.getCategoryId() != null) {
            existingHomeCategory.setCategoryId(homeCategory.getCategoryId());
        }

        return homeCategoryRepository.save(existingHomeCategory);
    }

    @Override
    public List<HomeCategory> getAllHomeCategories() {
        return homeCategoryRepository.findAll();
    }

}
