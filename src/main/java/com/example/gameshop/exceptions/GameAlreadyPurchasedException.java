package com.example.gameshop.exceptions;

public class GameAlreadyPurchasedException extends RuntimeException {
    public GameAlreadyPurchasedException(String message) {
        super(message);
    }
}
