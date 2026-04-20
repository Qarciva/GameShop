package com.example.gameshop.services;

import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.Order;
import com.example.gameshop.entities.User;
import com.example.gameshop.events.OrderCompleteEvent;
import com.example.gameshop.exceptions.GameAlreadyPurchasedException;
import com.example.gameshop.exceptions.GameNotAvailableException;
import com.example.gameshop.exceptions.GameNotFoundException;
import com.example.gameshop.exceptions.UserNotFoundException;
import com.example.gameshop.repositories.GameRepository;
import com.example.gameshop.repositories.OrderRepository;
import com.example.gameshop.repositories.UserRepository;
import com.example.gameshop.utils.UserManager;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final GameRepository gameRepository;
    private final UserManager userManager;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    @Transactional
    public void placeOrder(Long gameId){
        Game game = gameRepository.findById(gameId).orElseThrow(
                () -> new GameNotFoundException("Game not found")
        );
        User user = userRepository.findFullUserForCheckout(userManager.getCurrentUsername()).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        placeOrder(game,user);
    }
    @Transactional
    public void placeOrder(Game game, User user){
        if(!game.isAvailable())
            throw new GameNotAvailableException("Game not available");
        boolean alreadyOwned = user.getLibrary().stream()
                .anyMatch(ownedGame -> ownedGame.getId().equals(game.getId()));
        if(alreadyOwned)
            throw new GameAlreadyPurchasedException("Game already purchased  " + game.getId());
        Order order = Order.builder().
                user(user).
                game(game).
                purchasePrice(game.getPrice()).
                build();
        user.getOrders().add(order);
        user.getLibrary().add(game);
        orderRepository.save(order);
        eventPublisher.publishEvent(new OrderCompleteEvent(user,game.getPrice(), game.getName()));
    }
}
