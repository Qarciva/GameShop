package com.example.gameshop.Mappers;

import com.example.gameshop.dtos.OrderResponseDto;
import com.example.gameshop.entities.Order;

public class OrderMapper {
    public static OrderResponseDto convertOrderToDto(Order order){
        return OrderResponseDto.builder().
                gameId(order.getGame().getId()).
                purchaseDate(order.getPurchaseDate()).
                purchasePrice(order.getPurchasePrice()).
                orderId(order.getId()).
                gameName(order.getGame().getName()).
                build();
    }
}
