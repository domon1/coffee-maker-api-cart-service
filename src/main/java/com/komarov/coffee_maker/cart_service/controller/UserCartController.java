package com.komarov.coffee_maker.cart_service.controller;

import com.komarov.coffee_maker.cart_service.model.CartItem;
import com.komarov.coffee_maker.cart_service.model.UserCart;
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
    public ResponseEntity<UserCart> findCartByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(userCartService.findCartByUserId(userId));
    }

    @PostMapping(path = "/add/{userId}")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        userCartService.addToCart(userId, cartItem);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/{userId}/remove/{itemId}")
    public ResponseEntity<?> addToCart(@PathVariable Long userId, @PathVariable Long itemId) {
        userCartService.removeFromCart(userId, itemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/clear/{userId}")
    public ResponseEntity<?> clearCart(@PathVariable Long userId) {
        userCartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping(path = "/quantity/{userId}/{itemId}")
    public ResponseEntity<UserCart> changeQuantity(@PathVariable Long userId, @PathVariable Long itemId) {
        userCartService.changeQuantity(userId, true, itemId);
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/change/{userId}")
    public ResponseEntity<?> changeCartItem(@PathVariable Long userId, @RequestBody CartItem cartItem) {
        userCartService.changeCartItem(userId, cartItem);
        return ResponseEntity.ok().build();
    }
}
