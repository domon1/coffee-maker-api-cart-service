package com.komarov.coffee_maker.cart_service.model.DTO;

import com.komarov.coffee_maker.cart_service.model.CartItemIngredient;

public record CartItemIngredientDTO(
        Long id,
        Long ingredientId
) {
    public static CartItemIngredientDTO from(CartItemIngredient ingredient) {
        return new CartItemIngredientDTO(
                ingredient.getId(),
                ingredient.getIngredientId()
        );
    }
}
