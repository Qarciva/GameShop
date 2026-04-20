package com.example.gameshop.repositories;

import com.example.gameshop.entities.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {


    boolean existsByNameAndIdNot(String name, Long id);

    boolean existsByName(String name);
}
