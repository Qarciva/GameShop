package com.example.gameshop.repositories;

import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.Order;
import com.example.gameshop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    boolean existsOrderByGameAndUser(Game game, User user);

    @Query("SELECT o FROM Order o JOIN FETCH o.game WHERE o.user.id = :userId")
    List<Order> findOrdersByUserId(@Param("userId") Long userId);

    boolean existsOrderByGameIdAndUserId(Long gameId, Long id);
}
