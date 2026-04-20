package com.example.gameshop.events;

import com.example.gameshop.entities.Cart;
import com.example.gameshop.entities.Order;
import com.example.gameshop.entities.User;

public record OrderCompleteEvent(User user, Cart cart, double totalPrice) {
}
