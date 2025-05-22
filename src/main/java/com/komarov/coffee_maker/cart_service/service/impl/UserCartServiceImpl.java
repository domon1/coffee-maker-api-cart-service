package com.komarov.coffee_maker.cart_service.service.impl;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.CartItemIngredient;
import com.komarov.coffee_maker.cart_service.model.UserCart;
import com.komarov.coffee_maker.cart_service.repository.CartItemIngredientRepository;
import com.komarov.coffee_maker.cart_service.repository.CartItemRepository;
import com.komarov.coffee_maker.cart_service.repository.UserCartRepository;
import com.komarov.coffee_maker.cart_service.service.UserCartService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Service
public class UserCartServiceImpl implements UserCartService {
    final UserCartRepository cartRepository;
    final CartItemIngredientRepository ingredientRepository;
    final CartItemRepository itemRepository;

    public UserCartServiceImpl(UserCartRepository cartRepository, CartItemIngredientRepository ingredientRepository, CartItemRepository itemRepository) {
        this.cartRepository = cartRepository;
        this.ingredientRepository = ingredientRepository;
        this.itemRepository = itemRepository;
    }

    private UserCart createCart(Long userId) {
        UserCart cart = new UserCart();
        cart.setUserId(userId);
        cart.setCartItems(Set.of());
        return cartRepository.save(cart);
    }

    public UserCart findCartByUserId(Long userId) {
        return cartRepository.findFirstByUserId(userId)
                .orElseGet(() -> createCart(userId));
    }

    public void removeFromCart(Long userId, Long itemId) {
        UserCart cart = findCartByUserId(userId);
        cart.getCartItems().removeIf(item -> item.getId().equals(itemId));
        cartRepository.save(cart);
    }

    @Override
    public void changeQuantity(Long userId, boolean isInk, Long itemId) {
        UserCart cart = findCartByUserId(userId);
        for (CartItem item : cart.getCartItems()){
            if (item.getId().equals(itemId)){
                item.setQuantity(isInk ? item.getQuantity() + 1: item.getQuantity() - 1);
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public void changeCartItem(Long userId, CartItem cartItem) {
        UserCart cart = findCartByUserId(userId);
        for (CartItem item : cart.getCartItems()){
            if (item.getId().equals(cartItem.getId())){
                cart.getCartItems().remove(item);



                cart.getCartItems().add(cartItem);
            }
        }
        cartRepository.save(cart);
    }

    public void addToCart(Long userId, CartItem cartItem) {
        UserCart cart = findCartByUserId(userId);
        cartItem.setUserCart(cart);

        for (CartItem item : cart.getCartItems()) {
            if (Objects.equals(cartItem.getItemId(), item.getItemId())) {
                List<Long> presentIngredients = cartItem.getIngredients().stream()
                        .map(CartItemIngredient::getIngredientId).toList();
                List<Long> currentIngredients = item.getIngredients().stream()
                        .map(CartItemIngredient::getIngredientId).toList();
                if (Objects.equals(presentIngredients, currentIngredients)) {
                    item.setQuantity(item.getQuantity() + 1);
                    cartRepository.save(cart);
                    return;
                }
            }
        }

        Set<CartItemIngredient> ingredients = new HashSet<>();
        for (CartItemIngredient ingredient : cartItem.getIngredients()) {
            ingredients.add(ingredientRepository.save(ingredient));
        }

        CartItem item = new CartItem();
        item.setItemId(cartItem.getItemId());
        item.setQuantity(cartItem.getQuantity());
        item.setIngredients(ingredients);
        item.setUserCart(cart);
        item = itemRepository.save(item);

        for (CartItemIngredient ingredient : ingredients) {
            ingredient.setCartItem(item);
            ingredientRepository.save(ingredient);
        }

        cart.getCartItems().add(itemRepository.save(item));
        cartRepository.save(cart);
    }

    public void clearCart(Long userId) {
        cartRepository.delete(findCartByUserId(userId));
    }
}
