package com.senne.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.senne.modal.Cart;
import com.senne.modal.CartItem;
import com.senne.modal.Product;
import com.senne.modal.User;
import com.senne.request.AddItemRequest;
import com.senne.response.ApiResponse;
import com.senne.service.CartItemService;
import com.senne.service.CartService;
import com.senne.service.ProductService;
import com.senne.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;
    private final CartItemService cartItemService;
    private final UserService userService;
    private final ProductService productService;
    
    @GetMapping
    public ResponseEntity<Cart> findUserCartHandler(
        @RequestHeader("Authorization") String jwt) throws Exception{
        User user = userService.findUserByJwtToken(jwt);
        Cart cart = cartService.findUserCart(user);

        return new ResponseEntity<>(cart, HttpStatus.OK);
    }

    @PutMapping("/add")
    public ResponseEntity<CartItem> addItemToCart(
        @RequestBody AddItemRequest req, 
        @RequestHeader("Authorization") String jwt) 
        throws Exception {
        User user = userService.findUserByJwtToken(jwt);
        Product product = productService.findProductById(req.getProductId());

        CartItem item = cartService.addCartItem(user, product, req.getSize(), req.getQuantity());

        ApiResponse res = new ApiResponse();
        res.setMessage("Item added to cart successfully");

        return new ResponseEntity<>(item, HttpStatus.ACCEPTED);
    }   

    @DeleteMapping("/item/{cartItemId}")
    public ResponseEntity<ApiResponse> deleteCartItemHandler(
        @PathVariable Long cartItemId, 
        @RequestHeader("Authorization") String jwt) 
        throws Exception {

        User user = userService.findUserByJwtToken(jwt);
        cartItemService.removeCartItem(user.getId(), cartItemId);

        ApiResponse res = new ApiResponse();
        res.setMessage("Item removed from cart successfully");

        return new ResponseEntity<>(res, HttpStatus.ACCEPTED);
    }

    @PutMapping("/item/{cartItemId}")
    public ResponseEntity<CartItem> updateCartItemHandler(
        @PathVariable Long cartItemId, 
        @RequestBody CartItem cartItem, 
        @RequestHeader("Authorization") String jwt) 
        throws Exception {

        User user = userService.findUserByJwtToken(jwt);

        CartItem updatdCartItem = null;

        if(cartItem.getQuantity() > 0){
            updatdCartItem = cartItemService.updateCartItem(user.getId(), cartItemId, cartItem);
        }

        return new ResponseEntity<>(updatdCartItem, HttpStatus.ACCEPTED);
    }   
}
