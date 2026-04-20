package com.example.gameshop.events;

import com.example.gameshop.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class OrderCompleteEvent {
    private final User user;
    private final double totalPrice;
    private final String getNames;
}
