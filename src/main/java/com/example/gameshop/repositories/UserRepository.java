package com.example.gameshop.repositories;

import com.example.gameshop.dtos.UserResponseDto;
import com.example.gameshop.entities.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT new com.example.gameshop.dtos.UserResponseDto(u.id,u.username,u.email) FROM User u")
    List<UserResponseDto> findAllUsers();
    boolean existsUserByEmailOrUsername(String email, String username);

    boolean existsByEmailOrUsername(String email, String username);

    boolean existsByEmailAndIdNot(String email, Long id);

    boolean existsByUsernameAndIdNot(String username, Long id);

    boolean existsByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByEmailAndUsername(String email, String username);
    @EntityGraph(attributePaths = {"library", "library.genres"})
    Optional<User> findWithLibraryByUsername(String username);

    @EntityGraph(attributePaths = {"cart", "cart.items"})
    Optional<User> findWithCartByUsername(String username);

    @EntityGraph(attributePaths = {"cart", "cart.items", "cart.items.game"})
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserWithCart(String username);

    @EntityGraph(attributePaths = {"library", "orders"})
    @Query("SELECT u FROM User u WHERE u.username = :username")
    Optional<User> findUserWithLibraryAndOrders(String username);

    Optional<User> findByUsername(String username);

    @EntityGraph(attributePaths = {"cart", "cart.items", "cart.items.game", "library", "orders"})
    @Query("SELECT DISTINCT u FROM User u WHERE u.username = :username")
    Optional<User> findFullUserForCheckout(String username);
}
