package com.example.gameshop.repositories;

import com.example.gameshop.entities.ReviewReaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewReactionRepository extends JpaRepository<ReviewReaction, Long>{

    boolean existsByUserIdAndReviewId(Long userId, Long reviewId);

    ReviewReaction findByUserIdAndReviewId(Long userId, Long reviewId);
}

