package com.example.gameshop.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
public class OrderResponseDto {
    long gameId;
    long orderId;
    String gameName;
    LocalDateTime purchaseDate;
    double purchasePrice;
}
