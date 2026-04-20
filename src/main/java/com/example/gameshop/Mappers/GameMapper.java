package com.example.gameshop.Mappers;

import com.example.gameshop.dtos.GameDto;
import com.example.gameshop.dtos.GameResponseDto;
import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.Genre;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameMapper {
    public static Game convertToGame(GameDto gameDto){
        Game game = new Game();
        if(gameDto.getId() != null)
            game.setId(gameDto.getId());
        game.setName(gameDto.getName());
        game.setDescription(gameDto.getDescription());
        game.setPrice(gameDto.getPrice());
        game.setAvailable(gameDto.isAvailable());
        return game;
    }
    public static GameDto convertToDto(Game game){
        GameDto gameDto = new GameDto();
        gameDto.setId(game.getId());
        gameDto.setName(game.getName());
        gameDto.setDescription(game.getDescription());
        gameDto.setPrice(game.getPrice());
        gameDto.setAvailable(game.isAvailable());
        gameDto.setAverageRating(game.getAverageRating());
        if (game.getGenres() != null) {
            gameDto.setGenreNames(game.getGenres().stream()
                    .map(Genre::getName)
                    .toList());
        }
        return gameDto;
    }
    public static List<GameDto> convertToDtos(List<Game> games){
        return games.stream().map(GameMapper::convertToDto).toList();
    }
    public static Set<GameResponseDto> convertToResponseDto(Set<Game> games){
        Set<GameResponseDto> gameResponseDtos = new HashSet<>();
        for(Game game : games){
            GameResponseDto gameResponseDto = new GameResponseDto();
            gameResponseDto.setName(game.getName());
            for(Genre genre : game.getGenres()){
                gameResponseDto.getGenres().add(genre.getName());
            }
            gameResponseDtos.add(gameResponseDto);
        }
        return gameResponseDtos;
    }

}
