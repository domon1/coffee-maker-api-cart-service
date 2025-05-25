package com.komarov.coffee_maker.cart_service.controller;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.DTO.CartDTO;
import com.komarov.coffee_maker.cart_service.model.DTO.view.CartViewDTO;
import com.komarov.coffee_maker.cart_service.service.impl.UserCartServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping(path = "api/v1/cart")
public class UserCartController {
    final UserCartServiceImpl userCartService;

    public UserCartController(UserCartServiceImpl userCartService) {
        this.userCartService = userCartService;
    }

    @GetMapping(path = "/{userId}")
    public ResponseEntity<CartDTO> findCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userCartService.findCartDTOByUserId(userId));
    }

    @GetMapping(path = "/view/{userId}")
    public ResponseEntity<CartViewDTO> findCartViewByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userCartService.findCartViewDTO(userId));
    }

    @PutMapping(path = "/add/{userId}")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        userCartService.addToCart(userId, cartItem);
        return ResponseEntity.ok().build();
    }

    @PutMapping(path = "/{userId}/remove/{itemId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long userId, @PathVariable Long itemId) {
        userCartService.removeFromCart(userId, itemId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        userCartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/{userId}/item/{itemId}/quantity/{isInk}")
    public ResponseEntity<?> changeQuantity(@PathVariable Long userId, @PathVariable Long itemId, @PathVariable Boolean isInk) {
        userCartService.changeQuantity(userId, isInk, itemId);
        return ResponseEntity.ok().build();
    }
}
