package com.senne.service;

import com.senne.modal.HomeCategory;
import com.senne.modal.HomePage;

import java.util.List;

public interface HomeService {
    public HomePage createHomePageData(List<HomeCategory> allCategories);
}
