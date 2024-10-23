package com.senne.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.senne.modal.HomeCategory;
import com.senne.modal.HomePage;
import com.senne.service.HomeCategoryService;
import com.senne.service.HomeService;

import java.util.List;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class HomeCategoryController {

    private final HomeCategoryService homeCategoryService;
    private final HomeService homeService;

    @PostMapping("/home/categories")
    public ResponseEntity<HomePage> createHomeCategories(
        @RequestBody List<HomeCategory> homeCategories
    ) {
        List<HomeCategory> categories = homeCategoryService.createHomeCategories(homeCategories);
        HomePage homePage = homeService.createHomePageData(categories);
        return new ResponseEntity<>(homePage, HttpStatus.ACCEPTED);
    }

    @GetMapping("/admin/home-category")
    public ResponseEntity<List<HomeCategory>> getHomeCategory() {
        List<HomeCategory> homeCategories = homeCategoryService.getAllHomeCategories();
        return new ResponseEntity<>(homeCategories, HttpStatus.OK);
    }

    @PatchMapping("/admin/home-category/{id}")
    public ResponseEntity<HomeCategory> updateHomeCategory(
        @PathVariable Long id,
        @RequestBody HomeCategory homeCategory
    ) throws Exception {
        HomeCategory updatedHomeCategory = homeCategoryService.updateHomeCategory(homeCategory, id);
        return new ResponseEntity<>(updatedHomeCategory, HttpStatus.ACCEPTED);
    }
}
