package com.senne.service.impl;

import org.springframework.stereotype.Service;

import com.senne.modal.Product;
import com.senne.modal.User;
import com.senne.modal.WishList;
import com.senne.repository.WishlistRepository;
import com.senne.service.WishlistService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistRepository wishlistRepository;

    @Override
    public WishList createWishList(User user) {
        WishList wishList = new WishList();
        wishList.setUser(user);
        return wishlistRepository.save(wishList);
    }

    @Override
    public WishList getWishListByUser(User user) {
        WishList wishList = wishlistRepository.findByUser(user);
        if(wishList == null) {
            return createWishList(user);
        }
        return wishList;
    }

    @Override
    public WishList addProductToWishList(User user, Product product) {
        WishList wishList = getWishListByUser(user);

        if(wishList.getProducts().contains(product)) {
            wishList.getProducts().remove(product);
        } else wishList.getProducts().add(product);
        
        return wishlistRepository.save(wishList);
    }

}
