package com.example.gameshop.Mappers;

import com.example.gameshop.dtos.RegisterUserDto;
import com.example.gameshop.dtos.UserResponseDto;
import com.example.gameshop.entities.User;

import java.util.List;

public class UserMapper {
    public static User convertToUser(RegisterUserDto userDto){
        if(userDto == null)
            return null;
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        return user;
    }
    public static UserResponseDto convertToUserDto(User user){
        if(user == null)
            return null;

        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        return userResponseDto;
    }
    public static List<UserResponseDto> convertToUserDtos(List<User> users){
        return users.stream().map(UserMapper::convertToUserDto).toList();

    }
}
