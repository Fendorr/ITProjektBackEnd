package com.restful.api.h2.example.Control.mapper;

import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Entity.User;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserMapper {

    public User dtoToEntity(UserDTO userDto){
        User result = new User();
        result.setFirstName(userDto.getFirstName());
        result.setLastName(userDto.getLastName());
        result.setCreatedAt(new Date());
        return result;

    }

    public UserDTO entityToDto(User user){
        UserDTO result = new UserDTO();
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setCreatedAt(user.getCreatedAt());
        result.setId(user.getId());
        return result;
    }
}
