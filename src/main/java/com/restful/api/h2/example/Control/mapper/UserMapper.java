package com.restful.api.h2.example.Control.mapper;

import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Entity.User;
import com.restful.api.h2.example.Entity.userType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class UserMapper {

    @Autowired
    PasswordEncoder passwordEncoder;

    public User dtoToEntity(UserDTO userDto){
        User result = new User();
        result.setFirstName(userDto.getFirstName());
        result.setLastName(userDto.getLastName());
        result.setCreatedAt(new Date());
        result.setEmail(userDto.getEmail());
        //result.setPassword(passwordEncoder.encode(userDto.getPassword()));
        result.setFaculty(userDto.getFaculty());
        result.setType(userDto.getType());
        result.setActiveProject(userDto.getActiveProject());
        result.setLikedProjects(userDto.getLikedProjects());
        return result;
    }

    public UserDTO entityToDto(User user){
        UserDTO result = new UserDTO();
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setCreatedAt(user.getCreatedAt());
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setFaculty(user.getFaculty());
        result.setType(user.getType());
        result.setActiveProject(user.getActiveProject());
        result.setLikedProjects(user.getLikedProjects());
        return result;
    }
}
