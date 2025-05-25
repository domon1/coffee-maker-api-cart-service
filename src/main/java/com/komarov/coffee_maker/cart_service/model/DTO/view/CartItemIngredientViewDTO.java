package com.komarov.coffee_maker.cart_service.model.DTO.view;

import java.math.BigDecimal;

public record CartItemIngredientViewDTO(
        Long id,
        String name,
        BigDecimal price
) {
}
