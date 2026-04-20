package com.example.gameshop.Mappers;

import com.example.gameshop.dtos.ReactionUserDto;
import com.example.gameshop.dtos.ReviewCommentDto;
import com.example.gameshop.dtos.ReviewResponseDto;
import com.example.gameshop.entities.Review;
import com.example.gameshop.entities.ReviewComment;
import com.example.gameshop.entities.ReviewReaction;

import java.util.ArrayList;
import java.util.List;

public class ReviewMapper {
    public static ReviewResponseDto convertToResponseDto(Review review){
        List<ReactionUserDto> voters = new ArrayList<>();
        List<ReviewCommentDto> commentDtos = new ArrayList<>();
        for (ReviewReaction reaction : review.getReactions()) {
            voters.add(new ReactionUserDto(reaction.getUser().getUsername(),reaction.isPositive()));
        }
        for(ReviewComment comments:review.getComments()) {
            ReviewCommentDto commentDto = new ReviewCommentDto();
            commentDto.setComment(comments.getComment());
            commentDto.setReplyTo(comments.getReplyTo());
            commentDto.setUsername(comments.getUser().getUsername());
            commentDtos.add(commentDto);

        }

        return ReviewResponseDto.builder().
                username(review.getUser().getUsername()).
                comment(review.getText()).
                rating(review.getRating()).
                gameName(review.getGame().getName()).
                voters(voters).
                likes(review.getLikeCount()).
                dislikes(review.getDislikeCount()).
                comments(commentDtos).
                build();
    }
}
