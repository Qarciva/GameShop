package com.example.gameshop.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GenreDto {
    private Long id;
    @NotBlank(message = "Name cannot be blank")
    private String name;

}
