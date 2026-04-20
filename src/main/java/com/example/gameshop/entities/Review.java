package com.example.gameshop.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "review",orphanRemoval = true,cascade = CascadeType.ALL)
    private List<ReviewComment> comments = new ArrayList<>();
    private String text;
    @Max(value = 10, message = "Rating must be between 1 and 10")
    @Min(value = 1, message = "Rating must be between 1 and 10")
    private int rating;
    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne()
    @JoinColumn(name = "game_id")
    private Game game;
    @OneToMany(mappedBy = "review", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReviewReaction> reactions = new ArrayList<>();
    @Builder.Default
    private Long likeCount = 0L;
    @Builder.Default
    private Long dislikeCount = 0L;
}
