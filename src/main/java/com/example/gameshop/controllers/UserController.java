package com.example.gameshop.controllers;

import com.example.gameshop.dtos.GameResponseDto;
import com.example.gameshop.dtos.OrderResponseDto;
import com.example.gameshop.dtos.RegisterUserDto;
import com.example.gameshop.dtos.UserResponseDto;
import com.example.gameshop.services.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;
    @GetMapping("/ownedGames")
    public ResponseEntity<Set<GameResponseDto>> getOwnedGames(){
        System.out.println("getOwnedGames called");
        return ResponseEntity.ok(userService.getOwnedGames());
    }
    @GetMapping("/getOrders/{userId}")
    public ResponseEntity<List<OrderResponseDto>> getOrders(@PathVariable Long userId){
        return ResponseEntity.ok(userService.getOrderHistory(userId));
    }
    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> registerUser(@RequestBody @Valid RegisterUserDto userDto){
        return ResponseEntity.ok(userService.registerUser(userDto));
    }
    @GetMapping
    public ResponseEntity<List<UserResponseDto>> getAllUsers(){
        return ResponseEntity.ok(userService.getAll());
    }
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> findUserById(@PathVariable Long id){
        return ResponseEntity.ok(userService.findUserById(id));
    }
    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable Long id, @RequestBody @Valid RegisterUserDto userDto){
        return ResponseEntity.ok(userService.updateUser(id,userDto));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
