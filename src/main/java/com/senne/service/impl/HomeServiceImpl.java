package com.senne.service.impl;

import java.util.List;
import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.senne.domain.HomeCategorySection;
import com.senne.modal.Deal;
import com.senne.modal.HomeCategory;
import com.senne.modal.HomePage;
import com.senne.repository.DealRepository;
import com.senne.service.HomeService;

import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    private final DealRepository dealRepository;
    
    @Override
    public HomePage createHomePageData(List<HomeCategory> allCategories) {

        List<HomeCategory> gridCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.GRID)
                .collect(Collectors.toList());
        
        List<HomeCategory> shopByCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.SHOP_BY_CATEGORIES)
                .collect(Collectors.toList());
        
        List<HomeCategory> electricCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.ELECTRIC_CATEGORIES)
                .collect(Collectors.toList());
        
        List<HomeCategory> dealsCategories = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                .collect(Collectors.toList());
        
        List<Deal> createdDeals = new ArrayList<>();

        if(dealRepository.findAll().isEmpty()) {
            List<Deal> deals = allCategories.stream()
                .filter(category -> category.getSection() == HomeCategorySection.DEALS)
                .map(category -> new Deal(null, 10, category))
                .collect(Collectors.toList());
            
            createdDeals = dealRepository.saveAll(deals);
        } else createdDeals = dealRepository.findAll();

        HomePage homePage = new HomePage();
        homePage.setGrid(gridCategories);
        homePage.setShopByCategories(shopByCategories);
        homePage.setElectricCategories(electricCategories);
        homePage.setDeals(createdDeals);
        homePage.setDealCategories(dealsCategories);
        
        return homePage;
    }

    
}
