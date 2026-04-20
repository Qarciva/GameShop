package com.example.gameshop.dtos;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GameDto {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @Positive(message = "Price must be positive")
    private double price;
    private boolean available;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Long> genreIds;
    private List<String> genreNames;
    private Double averageRating;
}
