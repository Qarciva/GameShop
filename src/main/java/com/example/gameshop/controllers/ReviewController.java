package com.example.gameshop.controllers;

import com.example.gameshop.dtos.CreateReviewDto;
import com.example.gameshop.dtos.ReviewCommentDto;
import com.example.gameshop.dtos.ReviewResponseDto;
import com.example.gameshop.entities.ReviewReaction;
import com.example.gameshop.services.ReviewService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/reviews")
public class ReviewController {
    private final ReviewService reviewService;
    @PostMapping
    public ResponseEntity<String> createReview(@Valid @RequestBody CreateReviewDto createReviewDto){
        reviewService.saveReview(createReviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
    }
    @GetMapping("/{gameId}")
    public ResponseEntity<List<ReviewResponseDto>> getReviewsByGame(@PathVariable Long gameId){
        return ResponseEntity.ok(reviewService.getReviewsByGame(gameId));
    }
    @PostMapping("/addReaction/{reviewId}")
    public ResponseEntity<String> addReaction(@RequestBody ReviewReaction reaction, @PathVariable Long reviewId){
        reviewService.addReaction(reviewId,reaction);
        return ResponseEntity.status(HttpStatus.CREATED).body("Review added successfully");
    }
    @PostMapping("/addComment/{reviewId}")
    public ResponseEntity<String> addComment(@Valid @RequestBody ReviewCommentDto reviewCommentDto, @PathVariable Long reviewId){
        reviewService.addComment(reviewCommentDto,reviewId);
        return ResponseEntity.status(HttpStatus.CREATED).body("Comment added successfully");
    }
}
