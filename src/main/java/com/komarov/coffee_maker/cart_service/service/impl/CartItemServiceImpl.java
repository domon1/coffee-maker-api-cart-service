package com.komarov.coffee_maker.cart_service.service.impl;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.CartItemIngredient;
import com.komarov.coffee_maker.cart_service.model.DTO.view.CartItemViewDTO;
import com.komarov.coffee_maker.cart_service.model.UserCart;
import com.komarov.coffee_maker.cart_service.repository.CartItemRepository;
import com.komarov.coffee_maker.cart_service.service.CartItemIngredientService;
import com.komarov.coffee_maker.cart_service.service.CartItemService;
import com.komarov.coffee_maker.cart_service.util.ingredient.IngredientDTO;
import com.komarov.coffee_maker.cart_service.util.item.ItemDTO;
import com.komarov.coffee_maker.cart_service.util.item.ItemServiceFeign;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;


@Service
public class CartItemServiceImpl implements CartItemService {
    final CartItemRepository cartItemRepository;
    final CartItemIngredientService cartItemIngredientService;
    final ItemServiceFeign itemServiceFeign;

    public CartItemServiceImpl(CartItemRepository cartItemRepository, CartItemIngredientService cartItemIngredientService, ItemServiceFeign itemServiceFeign) {
        this.cartItemRepository = cartItemRepository;
        this.cartItemIngredientService = cartItemIngredientService;
        this.itemServiceFeign = itemServiceFeign;
    }

    @Override
    public CartItem createCartItem(UserCart cart, CartItem cartItem) {
        CartItem item = new CartItem();
        item.setItemId(cartItem.getItemId());
        item.setQuantity(cartItem.getQuantity());
        item.setUserCart(cart);
        item = cartItemRepository.save(item);

        List<CartItemIngredient> ingredients = cartItemIngredientService.createCartItemIngredients(cartItem.getIngredients(), item);
        item.setIngredients(new HashSet<>(ingredients));

        return item;
    }

    @Override
    public boolean isExistItem(Set<CartItem> items, CartItem check) {
        for (CartItem item : items) {
            if (Objects.equals(item.getItemId(), check.getItemId())) {
                List<Long> presentIngredients = check.getIngredients().stream()
                        .map(CartItemIngredient::getIngredientId).sorted().toList();
                List<Long> currentIngredients = item.getIngredients().stream()
                        .map(CartItemIngredient::getIngredientId).sorted().toList();
                if (Objects.equals(presentIngredients, currentIngredients)) {
                    item.setQuantity(item.getQuantity() + 1);
                    cartItemRepository.save(item);
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public List<CartItemViewDTO> findItemDTOsById(Set<CartItem> items) {
        List<CartItemViewDTO> itemDTOList = new ArrayList<>();
        for (CartItem item : items) {
            List<IngredientDTO> ingredients = cartItemIngredientService.findIngredientDTOsById(item.getIngredients());
            ItemDTO itemDTO = itemServiceFeign.findItemById(item.getItemId());
            itemDTOList.add(new CartItemViewDTO(
                    item.getId(),
                    itemDTO.name(),
                    calculatePrice(ingredients.stream().map(IngredientDTO::price).toList(), itemDTO.price()),
                    itemDTO.imageUrl(),
                    ingredients
            ));
        }

        return itemDTOList;
    }

    private BigDecimal calculatePrice(List<BigDecimal> item, BigDecimal itemPrice) {
        BigDecimal totalPrice = BigDecimal.ZERO;
        for (BigDecimal price : item) {
            totalPrice = totalPrice.add(price);
        }

        return totalPrice.add(itemPrice);
    }
}
