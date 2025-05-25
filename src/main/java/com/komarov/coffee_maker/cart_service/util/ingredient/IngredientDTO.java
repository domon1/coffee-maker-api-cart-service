package com.komarov.coffee_maker.cart_service.util.ingredient;

import java.math.BigDecimal;

public record IngredientDTO(
        Long id,
        String name,
        BigDecimal price
) {
}
