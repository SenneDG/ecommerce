package com.senne.service.impl;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;



import com.senne.modal.Cart;
import com.senne.modal.Coupon;
import com.senne.modal.User;
import com.senne.repository.CartRepository;
import com.senne.repository.CouponRepository;
import com.senne.repository.UserRepository;
import com.senne.service.CouponService;

import java.time.LocalDate;    

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;
    private final CartRepository cartRepository;
    private final UserRepository userRepository;
    
    @Override
    public Cart applyCoupon(String couponCode, double orderValue, User user) throws Exception {
        Coupon coupon = couponRepository.findByCode(couponCode);
        Cart cart = cartRepository.findByUserId(user.getId());

        if(coupon == null) {
            throw new Exception("Coupon not valid");
        }

        if(user.getUsedCoupons().contains(coupon)) {
            throw new Exception("Coupon already used");
        }

        if(coupon.getMinimumOrderValue() > orderValue) {
            throw new Exception("Minimum order value not reached");
        }

        if(coupon.isActive() && LocalDate.now().isAfter(coupon.getValidityStart()) && LocalDate.now().isBefore(coupon.getValidityEnd())) {
        
            user.getUsedCoupons().add(coupon);
            userRepository.save(user);

            double discountedPrice = (cart.getTotalSellingPrice() * coupon.getDiscountPercentage())/100;

            cart.setTotalSellingPrice(cart.getTotalSellingPrice() - discountedPrice);
            cart.setCouponCode(couponCode);
            cartRepository.save(cart);
            return cart;
        }

        throw new Exception("Coupon not valid");
    }

    @Override
    public Cart removeCoupon(String couponCode, User user) throws Exception {
        Coupon coupon = couponRepository.findByCode(couponCode);
        Cart cart = cartRepository.findByUserId(user.getId());
        
        if(coupon == null) {
            throw new Exception("Coupon not found");
        }

        double discountedPrice = (cart.getTotalSellingPrice() * coupon.getDiscountPercentage())/100;

        cart.setTotalSellingPrice(cart.getTotalSellingPrice() + discountedPrice);
        cart.setCouponCode(null);

        return cartRepository.save(cart);
    }

    @Override
    public Coupon findCouponById(Long id) throws Exception {
        return couponRepository.findById(id).orElseThrow(() ->
            new Exception("Coupon not found"));
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Override
    public List<Coupon> findAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteCoupon(Long id) throws Exception {
        findCouponById(id);
        couponRepository.deleteById(id);
    }

}
