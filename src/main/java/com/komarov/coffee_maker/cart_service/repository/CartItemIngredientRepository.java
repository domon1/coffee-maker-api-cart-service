package com.komarov.coffee_maker.cart_service.repository;

import com.komarov.coffee_maker.cart_service.model.CartItemIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemIngredientRepository extends JpaRepository<CartItemIngredient, Long> {
}
