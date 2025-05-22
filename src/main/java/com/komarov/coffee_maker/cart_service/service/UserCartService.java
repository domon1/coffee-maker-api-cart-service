package com.komarov.coffee_maker.cart_service.service;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.UserCart;
import org.springframework.stereotype.Service;

@Service
public interface UserCartService {
    UserCart findCartByUserId(Long userId);
    void addToCart(Long userId, CartItem cartItem);
    void removeFromCart(Long userId, Long itemId);
    void changeQuantity(Long userId, boolean isInk, Long itemId);
    void changeCartItem(Long userId, CartItem cartItem);
    void clearCart(Long userId);
}
