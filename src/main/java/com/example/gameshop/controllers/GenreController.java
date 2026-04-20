package com.example.gameshop.controllers;

import com.example.gameshop.dtos.GenreDto;
import com.example.gameshop.services.GenreService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/genres")
@AllArgsConstructor
public class GenreController {
    private final GenreService genreService;
    @GetMapping
    public ResponseEntity<List<GenreDto>> getAllGenres(){
        return ResponseEntity.ok(genreService.getAllGenres());
    }
    @GetMapping("/{id}")
    public ResponseEntity<GenreDto> getGenreById(@PathVariable Long id){
        return ResponseEntity.ok(genreService.getGenreById(id));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<GenreDto> deleteGenreById(@PathVariable Long id){
        genreService.deleteGenreById(id);
        return ResponseEntity.ok().build();
    }
    @PostMapping
    public ResponseEntity<GenreDto> saveGenre(@RequestBody @Valid GenreDto genreDto){
        return ResponseEntity.ok(genreService.save(genreDto));
    }
    @PutMapping("/{id}")
    public ResponseEntity<GenreDto> updateGenre(@PathVariable Long id,@RequestBody @Valid GenreDto genreDto){
        return ResponseEntity.ok(genreService.updateGenre(id,genreDto));
    }
}
