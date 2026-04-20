package com.example.gameshop.utils;

import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.Genre;
import com.example.gameshop.repositories.GameRepository;
import com.example.gameshop.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private final GameRepository gameRepository;
    private final GenreRepository genreRepository;
    @Override
    public void run(String... args) throws Exception {
        if (gameRepository.count() == 0) {

            Genre action = new Genre();
            action.setName("Action");
            genreRepository.save(action);

            for (int i = 1; i <= 25; i++) {
                Game game = new Game();
                game.setName("Test Game " + i);
                game.setDescription("Description for game number " + i);
                game.setPrice(10.0 + i);
                game.setAvailable(true);
                game.setGenres(Set.of(action));
                game.setAverageRating(0.0);

                gameRepository.save(game);
            }

            System.out.println("Test Game has been saved");
        }
    }
}
