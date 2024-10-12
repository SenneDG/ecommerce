package com.senne.service;

import java.util.List;

import com.senne.domain.AccountStatus;
import com.senne.exceptions.SellerException;
import com.senne.modal.Seller;

public interface SellerService {

    Seller getSellerProfile(String jwt) throws Exception;
    Seller createSeller(Seller seller) throws Exception;
    Seller getSellerById(Long id) throws SellerException;
    Seller getSellerByEmail(String email) throws Exception;
    List<Seller> getAllSellers(AccountStatus status) throws Exception;
    Seller updateSeller(Long id, Seller seller) throws Exception;
    void deleteSeller(Long id) throws Exception;
    Seller verifyEmail(String email, String otp) throws Exception;
    Seller updateSellerAccountStatus(Long selledId, AccountStatus status) throws Exception;
}
