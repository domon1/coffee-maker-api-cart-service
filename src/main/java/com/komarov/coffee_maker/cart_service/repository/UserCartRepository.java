package com.komarov.coffee_maker.cart_service.repository;

import com.komarov.coffee_maker.cart_service.model.UserCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCartRepository extends JpaRepository<UserCart, Long> {
    Optional<UserCart> findFirstByUserId(Long id);
}
