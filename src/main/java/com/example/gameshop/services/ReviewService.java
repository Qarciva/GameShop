package com.example.gameshop.services;

import com.example.gameshop.dtos.CreateReviewDto;
import com.example.gameshop.dtos.ReviewCommentDto;
import com.example.gameshop.dtos.ReviewResponseDto;
import com.example.gameshop.Mappers.ReviewMapper;
import com.example.gameshop.entities.*;
import com.example.gameshop.exceptions.*;
import com.example.gameshop.repositories.*;
import com.example.gameshop.utils.UserManager;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ReviewService {
    private final GameRepository gameRepository;
    private final ReviewRepository reviewRepository;
    private final OrderRepository orderRepository;
    private final UserManager userManager;
    private final ReviewReactionRepository reviewReactionRepository;
    @Transactional
    public void addComment(ReviewCommentDto commentDto,Long reviewId) {
        User user = userManager.getCurrentUserReference();
        if(commentDto.getReplyTo().equals( user.getUsername()))
            throw new IllegalArgumentException("You are not allowed to add reply yourself");
        Review review = reviewRepository.findById(reviewId).orElseThrow(
                () -> new ReviewNotFoundException("Review not found")
        );

        ReviewComment  reviewComment = new ReviewComment();
        reviewComment.setComment(commentDto.getComment());
        reviewComment.setUser(user);
        reviewComment.setReview(review);
        if(commentDto.getReplyTo() != null)
            reviewComment.setReplyTo(commentDto.getReplyTo());
        review.getComments().add(reviewComment);
    }
    @Transactional
    public void saveReview(CreateReviewDto createReviewDto){
        Game game = gameRepository.findById(createReviewDto.getGameId()).orElseThrow(() -> new GameNotFoundException("Game not found"));
        User user = userManager.getCurrentUser();
        if(!orderRepository.existsOrderByGameAndUser(game, user))
            throw new GameNotPurchasedException("You must purchase the game before reviewing it");
        if(reviewRepository.existsByUserAndGame(user,game))
            throw new ReviewAlreadyExistsException("You have already reviewed this game");
        Review review = Review.builder().
                user(user).
                game(game).
                rating(createReviewDto.getRating()).
                text(createReviewDto.getComment()).
                build();
        reviewRepository.save(review);
        Double rawAvg = reviewRepository.getAverageRatingByGameId(game.getId());
        double avg;
        if(rawAvg == null)
            avg = 0.0;
        else
            avg = Math.round(rawAvg * 10.0) / 10.0;
        game.setAverageRating(avg);
        gameRepository.save(game);

    }
    public List<ReviewResponseDto> getReviewsByGame(Long gameId){
        List<Review> reviews = reviewRepository.findReviewsByGameId(gameId);
        return reviews.stream().map(ReviewMapper::convertToResponseDto).toList();

    }
    @Transactional
    public void addReaction(Long reviewId,ReviewReaction reaction){
        User currentUser = userManager.getCurrentUserReference();
        Review  review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        ReviewReaction react = reviewReactionRepository.findByUserIdAndReviewId(currentUser.getId(), reviewId);
        if(react == null) {
            reaction.setUser(currentUser);
            reaction.setReview(review);
            if(reaction.isPositive())
                review.setLikeCount(review.getLikeCount()+1);
            else
                review.setDislikeCount(review.getDislikeCount()+1);
            review.getReactions().add(reaction);
        }
        else
            if(react.isPositive() == reaction.isPositive()) {
                reviewReactionRepository.delete(react);
                if(reaction.isPositive())
                    review.setLikeCount(review.getLikeCount()-1);
                else
                    review.setDislikeCount(review.getDislikeCount()-1);
            }
            else {
                react.setPositive(reaction.isPositive());
                if(reaction.isPositive()) {
                    review.setLikeCount(review.getLikeCount() + 1);
                    review.setDislikeCount(review.getDislikeCount() - 1);
                }

                else {
                    review.setDislikeCount(review.getDislikeCount() + 1);
                    review.setLikeCount(review.getLikeCount() - 1);
                }
            }

    }

}
