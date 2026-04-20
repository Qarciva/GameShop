package com.example.gameshop.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.example.gameshop.entities.User;
import com.example.gameshop.exceptions.UserNotFoundException;
import com.example.gameshop.repositories.UserRepository;

import lombok.AllArgsConstructor;
@AllArgsConstructor
@Component
public class UserManager {
    private final UserRepository userRepository;
    public User getCurrentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && auth.getPrincipal() instanceof User) {
            return (User) auth.getPrincipal();
        }

        String username = auth.getName();
        return userRepository.findWithLibraryByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }
    public User getCurrentUserReference(){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.getReferenceById(user.getId());
    }
    public String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            return auth.getName();
        }
        throw new UserNotFoundException("No authentication found");
    }

}
