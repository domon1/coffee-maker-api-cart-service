package com.komarov.coffee_maker.cart_service.model.DTO.view;

import java.math.BigDecimal;
import java.util.List;

public record CartViewDTO(
        Long userId,
        BigDecimal totalPrice,
        List<CartItemViewDTO> items
) {
}
