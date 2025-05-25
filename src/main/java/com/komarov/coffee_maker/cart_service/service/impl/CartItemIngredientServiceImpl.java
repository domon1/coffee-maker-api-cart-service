package com.komarov.coffee_maker.cart_service.service.impl;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.CartItemIngredient;
import com.komarov.coffee_maker.cart_service.model.DTO.view.CartItemIngredientViewDTO;
import com.komarov.coffee_maker.cart_service.repository.CartItemIngredientRepository;
import com.komarov.coffee_maker.cart_service.service.CartItemIngredientService;
import com.komarov.coffee_maker.cart_service.util.ingredient.IngredientDTO;
import com.komarov.coffee_maker.cart_service.util.ingredient.IngredientServiceFeign;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class CartItemIngredientServiceImpl implements CartItemIngredientService {
    final CartItemIngredientRepository cartItemIngredientRepository;
    final IngredientServiceFeign ingredientServiceFeign;

    public CartItemIngredientServiceImpl(CartItemIngredientRepository cartItemIngredientRepository, IngredientServiceFeign ingredientServiceFeign) {
        this.cartItemIngredientRepository = cartItemIngredientRepository;
        this.ingredientServiceFeign = ingredientServiceFeign;
    }

    @Override
    public CartItemIngredient createCartItemIngredient(CartItemIngredient ingredient, CartItem item) {
        CartItemIngredient cartItemIngredient = new CartItemIngredient();
        cartItemIngredient.setCartItem(item);
        cartItemIngredient.setIngredientId(ingredient.getIngredientId());

        return cartItemIngredient;
    }

    @Override
    public List<CartItemIngredient> createCartItemIngredients(Set<CartItemIngredient> itemIngredients, CartItem item) {
        Set<CartItemIngredient> ingredients = new HashSet<>();

        for (CartItemIngredient ingredient: itemIngredients) {
            ingredients.add(createCartItemIngredient(ingredient, item));
        }

        return cartItemIngredientRepository.saveAll(ingredients);
    }

    @Override
    public List<IngredientDTO> findIngredientDTOsById(Set<CartItemIngredient> ingredients) {
        List<IngredientDTO> ingredientDTOList = new ArrayList<>();
        for (CartItemIngredient ingredient: ingredients) {
            ingredientDTOList.add(ingredientServiceFeign.findIngredientById(ingredient.getIngredientId()));
        }

        return ingredientDTOList;
    }
}
