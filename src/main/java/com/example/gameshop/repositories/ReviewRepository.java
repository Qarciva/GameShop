package com.example.gameshop.repositories;

import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.Review;
import com.example.gameshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    boolean existsByUserAndGame(User user, Game game);
    @Query("SELECT AVG(r.rating) from Review r WHERE r.game.id = :gameId")
    Double getAverageRatingByGameId(@Param("gameId") Long gameId);
    List<Review> findReviewsByGameId(Long gameId);

    Optional<Review> findReviewById(Long id);
}
