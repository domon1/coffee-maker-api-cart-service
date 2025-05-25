package com.komarov.coffee_maker.cart_service.model.DTO;

import com.komarov.coffee_maker.cart_service.model.CartItem;

import java.util.List;

public record CartItemDTO(
        Long id,
        Long itemId,
        Integer quantity,
        List<CartItemIngredientDTO> ingredients
) {
    public static CartItemDTO from(CartItem item) {
        return new CartItemDTO(
                item.getId(),
                item.getItemId(),
                item.getQuantity(),
                item.getIngredients()
                        .stream()
                        .map(CartItemIngredientDTO::from)
                        .toList()
        );
    }
}
