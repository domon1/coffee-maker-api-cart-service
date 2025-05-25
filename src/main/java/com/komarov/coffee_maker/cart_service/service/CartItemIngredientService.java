package com.komarov.coffee_maker.cart_service.service;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.CartItemIngredient;
import com.komarov.coffee_maker.cart_service.util.ingredient.IngredientDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public interface CartItemIngredientService {
    CartItemIngredient createCartItemIngredient(CartItemIngredient ingredient, CartItem item);
    List<CartItemIngredient> createCartItemIngredients(Set<CartItemIngredient> itemIngredients, CartItem item);
    List<IngredientDTO> findIngredientDTOsById(Set<CartItemIngredient> ingredients);
}
