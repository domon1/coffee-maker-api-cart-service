package com.komarov.coffee_maker.cart_service.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
@Entity
public class CartItemIngredient implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long ingredientId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "cart_item_id")
    private CartItem cartItem;
}
