package com.example.gameshop.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;


@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler({
            GameAlreadyExistsException.class,
            GenreAlreadyExistsException.class,
            UserAlreadyExistsException.class,
            EmailAlreadyExistsException.class,
            UserNameAlreadyExistsException.class,
            ReviewAlreadyExistsException.class,
            GameNotAvailableException.class,
            GameAlreadyPurchasedException.class,
            GameAlreadyInCartExceptiom.class,
            ReviewReactionAlreadyFundException.class,
    })
    public ResponseEntity<Map<String, Object> >handleConflict(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.CONFLICT.value());

        return new ResponseEntity<>(body,HttpStatus.CONFLICT);
    }

    @ExceptionHandler({
            GameNotFoundException.class,
            GenreNotFoundException.class,
            UserNotFoundException.class,
            GameNotPurchasedException.class,
            CartNotFoundException.class,
            ReviewNotFoundException.class,
    })
    public ResponseEntity<Map<String,Object>> handleNotFound(Exception ex) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDate.now());
        body.put("message", ex.getMessage());
        body.put("status", HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(body,HttpStatus.NOT_FOUND);


    }
}
