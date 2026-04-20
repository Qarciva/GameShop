package com.example.gameshop.exceptions;

public class GameNotPurchasedException extends RuntimeException {
    public GameNotPurchasedException(String message) {
        super(message);
    }
}
