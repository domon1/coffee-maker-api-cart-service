package com.komarov.coffee_maker.cart_service.model.DTO.view;

import com.komarov.coffee_maker.cart_service.util.ingredient.IngredientDTO;

import java.math.BigDecimal;
import java.util.List;

public record CartItemViewDTO(
        Long id,
        String name,
        BigDecimal totalPrice,
        String imageUrl,
        List<IngredientDTO> ingredients
) {
}
