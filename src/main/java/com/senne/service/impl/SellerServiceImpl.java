package com.senne.service.impl;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.senne.config.JwtProvider;
import com.senne.domain.AccountStatus;
import com.senne.domain.USER_ROLE;
import com.senne.exceptions.SellerException;
import com.senne.modal.Address;
import com.senne.modal.Seller;
import com.senne.repository.AddressRepository;
import com.senne.repository.SellerRepository;
import com.senne.service.SellerService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {
    
    private final SellerRepository sellerRepository;
    private final AddressRepository addressRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    
    @Override
    public Seller getSellerProfile(String jwt) throws Exception {
        String email = jwtProvider.getEmailFromJwtToken(jwt);
        return this.getSellerByEmail(email);
    }

    @Override
    public Seller createSeller(Seller seller) throws Exception {
        Seller sellerExist = sellerRepository.findByEmail(seller.getEmail());

        if(sellerExist != null) {
            throw new Exception("Seller already exist with provided email - " + seller.getEmail() + ". Please try with different email.");
        }

        Address savedAddress = addressRepository.save(seller.getPickupAddress());
        
        Seller newSeller = new Seller();
        newSeller.setEmail(seller.getEmail());
        newSeller.setPassword(passwordEncoder.encode(seller.getPassword()));
        newSeller.setSellerName(seller.getSellerName());
        newSeller.setPickupAddress(savedAddress);
        newSeller.setGSTIN(seller.getGSTIN());
        newSeller.setRole(USER_ROLE.ROLE_SELLER);
        newSeller.setMobile(seller.getMobile());
        newSeller.setBankDetails(seller.getBankDetails());
        newSeller.setBusinessDetails(seller.getBusinessDetails());

        return sellerRepository.save(newSeller);
    }

    @Override
    public Seller getSellerById(Long id) throws SellerException {
        Seller seller = sellerRepository.findById(id)
            .orElseThrow(() -> new SellerException("Seller not found with provided id - " + id));
 
        return seller;
    }

    @Override
    public Seller getSellerByEmail(String email) throws Exception {
        Seller seller = sellerRepository.findByEmail(email);

        if(seller == null) {
            throw new Exception("Seller not found with provided email - " + email);
        }

        return seller;
    }

    @Override
    public List<Seller> getAllSellers(AccountStatus status) throws Exception {
        return sellerRepository.findByAccountStatus(status);
    }

    @Override
    public Seller updateSeller(Long id, Seller seller) throws Exception {
        Seller existingSeller = this.getSellerById(id);

        if(seller.getSellerName() != null) {
            existingSeller.setSellerName(seller.getSellerName());
        }

        if(seller.getMobile() != null) {
            existingSeller.setMobile(seller.getMobile());
        }

        if(seller.getEmail() != null) {
            existingSeller.setEmail(seller.getEmail());
        }

        if(seller.getBusinessDetails() != null && seller.getBusinessDetails().getBusinessName() != null) {
            existingSeller.getBusinessDetails().setBusinessName(seller.getBusinessDetails().getBusinessName());
        }

        if(seller.getBankDetails() != null && seller.getBankDetails().getAccountHolderName() != null && seller.getBankDetails().getIsfeCode() != null && seller.getBankDetails().getAcountNumber() != null) {
            existingSeller.getBankDetails().setAccountHolderName(seller.getBankDetails().getAccountHolderName());
            existingSeller.getBankDetails().setIsfeCode(seller.getBankDetails().getIsfeCode());
            existingSeller.getBankDetails().setAcountNumber(seller.getBankDetails().getAcountNumber());
        }

        if(seller.getPickupAddress() != null && seller.getPickupAddress().getAddress() != null && seller.getPickupAddress().getMobile() != null && seller.getPickupAddress().getCity() != null && seller.getPickupAddress().getState() != null) {
            existingSeller.getPickupAddress().setAddress(seller.getPickupAddress().getAddress());
            existingSeller.getPickupAddress().setMobile(seller.getPickupAddress().getMobile());
            existingSeller.getPickupAddress().setCity(seller.getPickupAddress().getCity());
            existingSeller.getPickupAddress().setState(seller.getPickupAddress().getState());
        }

        if(seller.getGSTIN() != null) {
            existingSeller.setGSTIN(seller.getGSTIN());
        }

        return sellerRepository.save(existingSeller);
    }

    @Override
    public void deleteSeller(Long id) throws Exception {
        Seller seller = this.getSellerById(id);
        sellerRepository.delete(seller);
    }

    @Override
    public Seller verifyEmail(String email, String otp) throws Exception {
        Seller seller = getSellerByEmail(email);
        seller.setEmailVerified(true);
        return sellerRepository.save(seller);
    }

    @Override
    public Seller updateSellerAccountStatus(Long selledId, AccountStatus status) throws Exception {
        Seller seller = this.getSellerById(selledId);
        seller.setAccountStatus(status);
        return sellerRepository.save(seller);
    }

}
