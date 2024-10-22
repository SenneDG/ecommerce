package com.senne.service;

import com.senne.modal.CartItem;

public interface CartItemService {

    CartItem updateCartItem(Long userId, Long cartItemId, CartItem cartItem) throws Exception;

    void removeCartItem(Long userId, Long cartItemId) throws Exception;

    CartItem findCartItemById(Long id) throws Exception;
}
