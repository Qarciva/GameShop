package com.example.gameshop.controllers;

import com.example.gameshop.dtos.GameDto;
import com.example.gameshop.services.GameService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@AllArgsConstructor
@RequestMapping("/games")
public class GameController {
    private final GameService gameService;
    @PostMapping
    public ResponseEntity<GameDto> saveGame(@RequestBody GameDto gameDto){
        return ResponseEntity.ok(gameService.save(gameDto));
    }
    @GetMapping
    public ResponseEntity<Page<GameDto>> getAllGames(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy
    ){
        return ResponseEntity.ok(gameService.getAllGames(page,size,sortBy));
    }
    @GetMapping("/all")
    public ResponseEntity<List<GameDto>> getAllGames(){
        return ResponseEntity.ok(gameService.getAllGames());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GameDto> getGameById(@PathVariable Long id){
        return ResponseEntity.ok(gameService.getGameById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGameById(@PathVariable Long id){
        gameService.deleteGameById(id);
        return ResponseEntity.ok("Game deleted successfully");
    }
    @PutMapping("/{id}")
    public ResponseEntity<GameDto> updateGame(@PathVariable Long id,@Valid @RequestBody GameDto gameDto){
        return ResponseEntity.ok(gameService.updateGame(id,gameDto));
    }

}
