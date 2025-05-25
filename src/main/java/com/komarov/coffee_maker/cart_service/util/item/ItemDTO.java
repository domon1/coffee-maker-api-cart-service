package com.komarov.coffee_maker.cart_service.util.item;

import java.math.BigDecimal;

public record ItemDTO(
        String name,
        BigDecimal price,
        String imageUrl
) {
}
