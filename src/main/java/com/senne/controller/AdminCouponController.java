package com.senne.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.senne.modal.Cart;
import com.senne.modal.Coupon;
import com.senne.modal.User;
import com.senne.service.CouponService;
import com.senne.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class AdminCouponController {

    private final CouponService couponService;
    private final UserService userService;
    // private final CartService cartService;

    @PostMapping("/apply")
    public ResponseEntity<Cart> applyCoupon(
        @RequestParam String apply,
        @RequestParam String code,
        @RequestParam double orderValue,
        @RequestHeader("Authorization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Cart cart;

        if(apply.equals("true")) {
            cart = couponService.applyCoupon(code, orderValue, user);
        } else {
            cart = couponService.removeCoupon(code, user);
        }

        return ResponseEntity.ok(cart);
    }

    @PostMapping("/admin/create")
    public ResponseEntity<Coupon> createCoupon(
        @RequestBody Coupon coupon
    ) {
        Coupon createdCoupon = couponService.createCoupon(coupon);
        return ResponseEntity.ok(createdCoupon);
    }

    @DeleteMapping("/admin/delete/{id}")
    public ResponseEntity<?> deleteCoupon(
        @PathVariable Long id
    ) throws Exception {
        couponService.deleteCoupon(id);
        return ResponseEntity.ok("Coupon deleted successfully");
    }

    @GetMapping("/admin/all")
    public ResponseEntity<?> getAllCoupons() {
        return ResponseEntity.ok(couponService.findAllCoupons());
    }
}
