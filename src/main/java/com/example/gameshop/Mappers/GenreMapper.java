package com.example.gameshop.Mappers;

import com.example.gameshop.dtos.GenreDto;
import com.example.gameshop.entities.Genre;

import java.util.List;

public class GenreMapper {
    public static GenreDto convertToGenreDto(Genre genre){
        return new GenreDto(genre.getId(), genre.getName());
    }
    public static Genre convertToGenre(GenreDto genreDto){
        Genre genre = new Genre();
        if(genreDto.getId() != null)
            genre.setId(genreDto.getId());
        genre.setName(genreDto.getName());
        return genre;
    }
    public static List<GenreDto> convertToGenreDtos(List<Genre> genres){
        return genres.stream().map(GenreMapper::convertToGenreDto).toList();
    }
}
