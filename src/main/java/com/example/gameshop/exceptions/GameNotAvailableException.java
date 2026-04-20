package com.example.gameshop.exceptions;

public class GameNotAvailableException extends RuntimeException {
    public GameNotAvailableException(String message) {
        super(message);
    }
}
