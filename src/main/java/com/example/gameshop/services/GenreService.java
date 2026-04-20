package com.example.gameshop.services;

import com.example.gameshop.dtos.GenreDto;
import com.example.gameshop.Mappers.GenreMapper;
import com.example.gameshop.entities.Genre;
import com.example.gameshop.exceptions.GenreAlreadyExistsException;
import com.example.gameshop.exceptions.GenreNotFoundException;
import com.example.gameshop.repositories.GenreRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class GenreService {
    private final GenreRepository genreRepository;
    public List<GenreDto> getAllGenres(){
        return GenreMapper.convertToGenreDtos(genreRepository.findAll());
    }
    public GenreDto save(GenreDto genreDto){
        if(genreRepository.existsByName(genreDto.getName()))
            throw new GenreAlreadyExistsException("Genre already exists");
        Genre genre = GenreMapper.convertToGenre(genreDto);
        return GenreMapper.convertToGenreDto(genreRepository.save(genre));
    }
    public GenreDto getGenreById(Long id){
        return GenreMapper.convertToGenreDto(genreRepository.findById(id).orElseThrow(
                () -> new GenreNotFoundException("Genre not found")
        ));
    }
    public void deleteGenreById(Long id){
        if(!genreRepository.existsById(id))
            throw new GenreNotFoundException("Genre not found");
        genreRepository.deleteById(id);
    }
    @Transactional
    public GenreDto updateGenre(Long id, GenreDto genreDto) {
        Genre genre = genreRepository.findById(id)
                .orElseThrow(() -> new GenreNotFoundException("Genre not found"));

        boolean exists = genreRepository.existsByNameAndIdNot(genreDto.getName(), id);
        if (exists) {
            throw new GenreAlreadyExistsException("Genre already exists");
        }

        genre.setName(genreDto.getName());

        Genre saved = genreRepository.save(genre);
        return GenreMapper.convertToGenreDto(saved);
    }

}
