package com.komarov.coffee_maker.cart_service.service.impl;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.DTO.CartDTO;
import com.komarov.coffee_maker.cart_service.model.DTO.view.CartItemViewDTO;
import com.komarov.coffee_maker.cart_service.model.DTO.view.CartViewDTO;
import com.komarov.coffee_maker.cart_service.model.UserCart;
import com.komarov.coffee_maker.cart_service.repository.UserCartRepository;
import com.komarov.coffee_maker.cart_service.service.CartItemIngredientService;
import com.komarov.coffee_maker.cart_service.service.CartItemService;
import com.komarov.coffee_maker.cart_service.service.UserCartService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;

@Service
public class UserCartServiceImpl implements UserCartService {
    final UserCartRepository cartRepository;
    final CartItemService itemService;
    final CartItemIngredientService ingredientService;



    public UserCartServiceImpl(UserCartRepository cartRepository, CartItemService itemService, CartItemIngredientService ingredientService) {
        this.cartRepository = cartRepository;
        this.itemService = itemService;
        this.ingredientService = ingredientService;
    }

    private UserCart createCart(Long userId) {
        UserCart cart = new UserCart();
        cart.setUserId(userId);
        cart.setCartItems(Set.of());
        return cartRepository.save(cart);
    }

    @Override
    public CartDTO findCartDTOByUserId(Long userId) {
        UserCart cart =  cartRepository.findFirstByUserId(userId)
                .orElseGet(() -> createCart(userId));
        return CartDTO.from(cart);
    }

    @Override
    public UserCart findCartByUserId(Long userId) {
        return cartRepository.findFirstByUserId(userId)
                .orElseGet(() -> createCart(userId));
    }

    @Override
    public void removeFromCart(Long userId, Long itemId) {
        UserCart cart = findCartByUserId(userId);
        cart.getCartItems().removeIf(item -> item.getId().equals(itemId));
        cartRepository.save(cart);
    }

    @Override
    public void changeQuantity(Long userId, boolean isInk, Long itemId) {
        UserCart cart = findCartByUserId(userId);
        for (CartItem item : cart.getCartItems()) {
            if (item.getId().equals(itemId)) {
                item.setQuantity(isInk ? item.getQuantity() + 1 : item.getQuantity() - 1);
            }
        }
        cartRepository.save(cart);
    }

    @Override
    public void addToCart(Long userId, CartItem cartItem) {
        UserCart cart = findCartByUserId(userId);

        if (itemService.isExistItem(cart.getCartItems(), cartItem)) return;

        itemService.createCartItem(cart, cartItem);
    }

    @Override
    public void clearCart(Long userId) {
        cartRepository.delete(findCartByUserId(userId));
    }

    @Override
    public CartViewDTO findCartViewDTO(Long userId) {
        UserCart cart = findCartByUserId(userId);
        List<CartItemViewDTO> itemDTOList = itemService.findItemDTOsById(cart.getCartItems());

        return new CartViewDTO(
                cart.getUserId(),
                calculatePrice(itemDTOList.stream().map(CartItemViewDTO::totalPrice).toList()),
                itemDTOList
        );
    }

    private BigDecimal calculatePrice(List<BigDecimal> item) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (BigDecimal price : item) {
            totalPrice = totalPrice.add(price);
        }

        return totalPrice;
    }
}
