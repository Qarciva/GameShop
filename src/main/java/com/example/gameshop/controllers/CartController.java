package com.example.gameshop.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.gameshop.services.CartService;

import lombok.AllArgsConstructor;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@AllArgsConstructor
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;
   
    @PostMapping("/add/{gameId}")
    public ResponseEntity<String> addToCart(@PathVariable Long gameId){
        cartService.addToCart(gameId);
        return ResponseEntity.ok("Game added to cart");
    }
    @PostMapping("/checkout")
    public ResponseEntity<String> checkout(){
        cartService.checkout();
        return ResponseEntity.ok("Checkout successful");
    }


}
