package com.komarov.coffee_maker.cart_service.service;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.DTO.view.CartItemViewDTO;
import com.komarov.coffee_maker.cart_service.model.UserCart;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CartItemService {
    CartItem createCartItem(UserCart cart, CartItem cartItem);
    boolean isExistItem(Set<CartItem> items, CartItem check);
    List<CartItemViewDTO> findItemDTOsById(Set<CartItem> items);
}
