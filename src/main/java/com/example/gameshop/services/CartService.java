package com.example.gameshop.services;

import com.example.gameshop.entities.Cart;
import com.example.gameshop.entities.CartItem;
import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.User;
import com.example.gameshop.events.OrderCompleteEvent;
import com.example.gameshop.exceptions.*;
import com.example.gameshop.repositories.CartRepository;
import com.example.gameshop.repositories.GameRepository;
import com.example.gameshop.repositories.OrderRepository;
import com.example.gameshop.repositories.UserRepository;
import com.example.gameshop.utils.UserManager;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class CartService {
    private final GameRepository gameRepository;
    private final CartRepository cartRepository;
    private final UserManager userManager;
    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final EmailService emailService;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    public List<CartItem> getItems() {
        User user = userRepository.findWithCartByUsername(userManager.getCurrentUsername()).orElseThrow(() -> new UserNotFoundException("User not found"));
        Cart cart = user.getCart();
        if(cart == null)
            return new ArrayList<>();
        return cart.getItems();
    }
    @Transactional
    public void addToCart(Long gameId){
        
       User user = userRepository.findWithCartByUsername(userManager.getCurrentUsername()).orElseThrow(
               () -> new UserNotFoundException("User not found")
       );
       if(orderRepository.existsOrderByGameIdAndUserId(gameId, user.getId()))
           throw new GameAlreadyPurchasedException("Game already purchased");
       Cart cart = user.getCart();
       Game game = gameRepository.findById(gameId).orElseThrow(() -> new GameNotFoundException("Game not found"));
        if(!game.isAvailable())
            throw new GameNotAvailableException("Game Not available");
        
       if(cart == null){
            cart = new Cart(); // Builder-ის ნაცვლად გამოიყენე კონსტრუქტორი სატესტოდ
            cart.setUser(user);
            cart.setItems(new ArrayList<>());
            user.setCart(cart);
            //cartRepository.save(cart);
       }
       else 
           if(cart.getItems().stream().anyMatch(item -> item.getGame().getId().equals(gameId)))
               throw new GameAlreadyInCartExceptiom("Game already in cart");
        CartItem cartItem = new CartItem();
        cartItem.setGame(game);
        cartItem.setCart(cart);
        cart.getItems().add(cartItem);
        cartRepository.save(cart);
        
    }
    @Transactional
    public void checkout(){
        String username = userManager.getCurrentUsername();
        User user = userRepository.findUserWithCart(username).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        Cart cart = user.getCart();
        if(cart == null || cart.getItems().isEmpty())
            throw new CartNotFoundException("Cart is empty");
        userRepository.findUserWithLibraryAndOrders(username);

        StringBuilder messageBody = new StringBuilder();
        messageBody.append("You have purchased the following games:\n");
        double totalPrice = 0.0;
        for(CartItem item : cart.getItems()){
            Game game = item.getGame();
            orderService.placeOrder(game,user);
            messageBody.append("- ").append(item.getGame().getName()).append("\n")
            .append(" ($")
            .append(item.getGame().getPrice())
            .append(")\n");
            totalPrice += item.getGame().getPrice();
        }
        messageBody.append("\nTotal Price: $").append(totalPrice);
        messageBody.append("\n\nThank you for your purchase!");
        cart.getItems().clear();
        cartRepository.save(cart);
        eventPublisher.publishEvent(new OrderCompleteEvent(user,cart,totalPrice));
        emailService.sendEmail(user.getEmail(), "Order Confirmation", messageBody.toString());
        
    }

}