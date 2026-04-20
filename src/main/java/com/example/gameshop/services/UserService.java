package com.example.gameshop.services;

import com.example.gameshop.dtos.GameResponseDto;
import com.example.gameshop.dtos.OrderResponseDto;
import com.example.gameshop.dtos.RegisterUserDto;
import com.example.gameshop.dtos.UserResponseDto;
import com.example.gameshop.Mappers.GameMapper;
import com.example.gameshop.Mappers.OrderMapper;
import com.example.gameshop.Mappers.UserMapper;
import com.example.gameshop.entities.Game;
import com.example.gameshop.entities.Order;
import com.example.gameshop.entities.User;
import com.example.gameshop.enums.Role;
import com.example.gameshop.exceptions.EmailAlreadyExistsException;
import com.example.gameshop.exceptions.UserAlreadyExistsException;
import com.example.gameshop.exceptions.UserNameAlreadyExistsException;
import com.example.gameshop.exceptions.UserNotFoundException;
import com.example.gameshop.repositories.OrderRepository;
import com.example.gameshop.repositories.UserRepository;
import com.example.gameshop.utils.UserManager;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final OrderRepository orderRepository;
    private final UserManager userManager;
    public List<UserResponseDto> getAll(){
        return userRepository.findAllUsers();
    }
    @Transactional
    public UserResponseDto registerUser(RegisterUserDto userDto){
        if(userRepository.existsByEmailAndUsername(userDto.getEmail(),userDto.getUsername()))
            throw new UserAlreadyExistsException("User with email and username already exists");
        if(userRepository.existsByEmail(userDto.getEmail()))
            throw new EmailAlreadyExistsException("Email already exists");
        if(userRepository.existsByUsername(userDto.getUsername()))
            throw new UserNameAlreadyExistsException("UserName already exists");
        User user = UserMapper.convertToUser(userDto);
        user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setRole(Role.ROLE_USER);
        User saved = userRepository.save(user);
        return UserMapper.convertToUserDto(saved);
    }
    public UserResponseDto findUserById(Long id){
        return UserMapper.convertToUserDto(userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        ));
    }
    @Transactional
    public UserResponseDto updateUser(Long id, RegisterUserDto userDto){
        User user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found")
        );
        if(userRepository.existsByEmailAndIdNot(userDto.getEmail(),id) ||
            userRepository.existsByUsernameAndIdNot(userDto.getUsername(),id)
        )
            throw new UserAlreadyExistsException("User already exists");
        user.setUsername(userDto.getUsername());
        if(userDto.getPassword() != null &&  !userDto.getPassword().isBlank())
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        user.setEmail(userDto.getEmail());
        return UserMapper.convertToUserDto(userRepository.save(user));

    }
    @Transactional
    public void deleteUserById(Long id){
        if(!userRepository.existsById(id))
            throw new UserNotFoundException("User not found");
        userRepository.deleteById(id);
    }
    public List<OrderResponseDto> getOrderHistory(Long userId){
        if(!userRepository.existsById(userId))
            throw new UserNotFoundException("User not found");
        List<Order> orders = orderRepository.findOrdersByUserId(userId);
        return orders.stream().map(OrderMapper::convertOrderToDto).toList();

    }
    public Set<GameResponseDto> getOwnedGames(){
       Set<Game> ownedGames = userManager.getCurrentUser().getLibrary();
       return GameMapper.convertToResponseDto(ownedGames);
    }

}
