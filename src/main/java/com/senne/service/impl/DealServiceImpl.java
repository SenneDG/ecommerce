package com.senne.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.senne.modal.Deal;
import com.senne.modal.HomeCategory;
import com.senne.repository.DealRepository;
import com.senne.repository.HomeCategoryRepository;
import com.senne.service.DealService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DealServiceImpl implements DealService {

    private final DealRepository dealRepository;
    private final HomeCategoryRepository homeCategoryRepository;
    
    @Override
    public List<Deal> getDeals() {
        return dealRepository.findAll();
    }

    @Override
    public Deal createDeal(Deal deal) {
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).get();
        Deal newDeal = dealRepository.save(deal);
        newDeal.setCategory(category);
        newDeal.setDiscount(deal.getDiscount());
    
        return dealRepository.save(newDeal);
    }

    @Override
    public Deal updateDeal(Deal deal, Long id) throws Exception {
        Deal existingDeal = dealRepository.findById(id).get();
        HomeCategory category = homeCategoryRepository.findById(deal.getCategory().getId()).get();

        if(existingDeal != null) {
            if(deal.getDiscount() != null) {
                existingDeal.setDiscount(deal.getDiscount());
            }
            if(category != null) {
                existingDeal.setCategory(category);
            }

            return dealRepository.save(existingDeal);
        }

        throw new Exception("Deal not found with id: " + id);
    }

    @Override
    public void deleteDeal(Long id) throws Exception {
        Deal deal = dealRepository.findById(id).orElseThrow(() ->
            new Exception("Deal not found with id: " + id));
        
        dealRepository.delete(deal);
    }

}