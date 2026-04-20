package com.example.gameshop.repositories;

import com.example.gameshop.entities.Game;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameRepository extends JpaRepository<Game, Long> {
    boolean existsGamesByName(String name);

    boolean existsByNameIgnoreCase(String name);

    List<Game> findGamesByName(String name);
    @EntityGraph(attributePaths = {"genres"})
    List<Game> findAll();
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);
    @Query("SELECT DISTINCT g FROM Game g LEFT JOIN FETCH g.genres")
    List<Game> findAllWithGenres();
    @Query(value = "SELECT DISTINCT g FROM Game g LEFT JOIN FETCH g.genres",
            countQuery = "SELECT count(g) FROM Game g")
    Page<Game> findAllWithGenres(Pageable pageable);
}
