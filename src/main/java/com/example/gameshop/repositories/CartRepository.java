package com.example.gameshop.repositories;

import com.example.gameshop.entities.Cart;
import com.example.gameshop.entities.CartItem;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CartRepository extends CrudRepository<Cart,Integer> {
    Optional<Cart> findById(Long id);
}
