package com.example.gameshop.services;

import com.example.gameshop.dtos.GameDto;
import com.example.gameshop.Mappers.GameMapper;
import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.Genre;
import com.example.gameshop.exceptions.GameAlreadyExistsException;
import com.example.gameshop.exceptions.GameNotFoundException;
import com.example.gameshop.exceptions.GenreNotFoundException;
import com.example.gameshop.repositories.GameRepository;
import com.example.gameshop.repositories.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;

    @Transactional
    public GameDto save(GameDto gameDto) {
        if(gameRepository.existsByNameIgnoreCase(gameDto.getName()))
            throw new GameAlreadyExistsException("Game already exists");

        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(gameDto.getGenreIds()));
        if(genres.size() != gameDto.getGenreIds().size())
            throw new GenreNotFoundException("Genres not found");
        Game game = GameMapper.convertToGame(gameDto);
        game.setGenres(genres);
        Game saved = gameRepository.save(game);
        return GameMapper.convertToDto(saved);
    }
    public Page<GameDto> getAllGames(int page, int size,String sortBy){
        Pageable pageable = PageRequest.of(page,size, Sort.by(sortBy).descending());
        Page<Game> games = gameRepository.findAllWithGenres(pageable);
        return games.map(GameMapper::convertToDto);

    }
    public List<GameDto> getAllGames(){
        List<Game> games = gameRepository.findAllWithGenres();
        return games.stream().map(GameMapper::convertToDto).toList();

    }
    public GameDto getGameById(Long id){
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException("Game not found")
        );
        return GameMapper.convertToDto(game);
    }
    public void deleteGameById(Long id){
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException("Game not found")
        );
        gameRepository.delete(game);

    }
    @Transactional
    public GameDto updateGame(Long id, GameDto gameDto){
        Game game = gameRepository.findById(id).orElseThrow(
                () -> new GameNotFoundException("Game not found")
        );
        boolean exists =  gameRepository.existsByNameIgnoreCaseAndIdNot(gameDto.getName(),id);
        if(exists)
            throw new GameAlreadyExistsException("Game already exists");
        game.setName(gameDto.getName());
        game.setDescription(gameDto.getDescription());
        game.setPrice(gameDto.getPrice());
        game.setAvailable(gameDto.isAvailable());
        Set<Genre> genres = new HashSet<>(genreRepository.findAllById(gameDto.getGenreIds()));
        if(genres.size() != gameDto.getGenreIds().size())
            throw new GenreNotFoundException("Genres not found");


        return GameMapper.convertToDto(gameRepository.save(game));

    }



}
