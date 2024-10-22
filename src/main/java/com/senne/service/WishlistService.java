package com.senne.service;

import com.senne.modal.Product;
import com.senne.modal.User;
import com.senne.modal.WishList;

public interface WishlistService {

    WishList createWishList(User user);
    WishList getWishListByUser(User user);
    WishList addProductToWishList(User user, Product product);
}

