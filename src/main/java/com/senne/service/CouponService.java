package com.senne.service;

import com.senne.modal.User;
import com.senne.modal.Cart;
import com.senne.modal.Coupon;

import java.util.List;

public interface CouponService {

    Cart applyCoupon(String couponCode, double orderValue, User user) throws Exception;
    Cart removeCoupon(String couponCode, User user) throws Exception;
    Coupon findCouponById(Long id) throws Exception;
    Coupon createCoupon(Coupon coupon);
    List<Coupon> findAllCoupons();
    void deleteCoupon(Long id) throws Exception;
}
