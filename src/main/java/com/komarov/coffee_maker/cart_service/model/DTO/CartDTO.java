package com.komarov.coffee_maker.cart_service.model.DTO;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.UserCart;

import java.util.List;

public record CartDTO(
        Long id,
        Long userId,
        List<CartItemDTO> cartItems
) {
    public static CartDTO from(UserCart cart) {
        return new CartDTO(
                cart.getId(),
                cart.getUserId(),
                cart.getCartItems()
                        .stream()
                        .map(CartItemDTO::from)
                        .toList()
        );
    }
}
