package com.senne.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senne.modal.Product;
import com.senne.modal.User;
import com.senne.modal.WishList;
import com.senne.service.UserService;
import com.senne.service.WishlistService;
import com.senne.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;
    private final UserService userService;
    private final ProductService productService;

    @GetMapping()
    public ResponseEntity<WishList> getWishlistByUserId(
        @RequestHeader("Authroization") String jwt
    ) throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        WishList wishList = wishlistService.getWishListByUser(user);

        return ResponseEntity.ok(wishList);
    }

    @PostMapping("/add-product/{productId}")
    public ResponseEntity<WishList> addProductToWishList(
        @RequestHeader("Authorization") String jwt,
        @PathVariable Long productId
    ) throws Exception {
        Product product = productService.findProductById(productId);
        User user = userService.findUserByJwtToken(jwt);    
        WishList updatedWishList = wishlistService.addProductToWishList(user, product);
        
        return ResponseEntity.ok(updatedWishList);
    }
}
