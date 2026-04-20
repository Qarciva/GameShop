package com.example.gameshop.dtos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReviewResponseDto {
    private String username;
    private String comment;
    private double rating;
    private String gameName;
    private Long likes;
    private Long dislikes;
    private List<ReactionUserDto> voters;
    private List<ReviewCommentDto> comments;
}
