package com.komarov.coffee_maker.cart_service.util.ingredient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ingredient-consumer", url = "http://localhost:8083/api/v1/ingredient")
public interface IngredientServiceFeign {
    @GetMapping(value = "/{id}")
    IngredientDTO findIngredientById(@PathVariable Long id);
}
