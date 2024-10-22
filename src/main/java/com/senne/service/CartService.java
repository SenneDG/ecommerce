package com.senne.service;

import com.senne.modal.Cart;
import com.senne.modal.CartItem;
import com.senne.modal.Product;
import com.senne.modal.User;

public interface CartService {

    public CartItem addCartItem(
        User user,
        Product product,
        String size,
        int quantity
    );

    public Cart findUserCart(User user);
}
