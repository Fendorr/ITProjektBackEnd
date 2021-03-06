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

    public User dtoToEntity(UserDTO userDto) {
        User result = new User();
        result.setFirstName(userDto.getFirstName());
        result.setLastName(userDto.getLastName());
        result.setCreatedAt(new Date());
        if (userDto.getEmail() != null || userDto.getEmail() != "undefined") {
            result.setEmail(userDto.getEmail());
        }
        result.setDescription(userDto.getDescription());
        result.setFaculty(userDto.getFaculty());
        result.setType(userDto.getType());
        result.setActiveProject(userDto.getActiveProject());
        result.setLikedProjects(userDto.getLikedProjects());
        result.setProjectInvites(userDto.getProjectInvites());
        result.setSentApplications(userDto.getSentApplications());
        result.setTags(userDto.getTags());
        return result;
    }

    public UserDTO entityToDto(User user) {
        UserDTO result = new UserDTO();
        result.setFirstName(user.getFirstName());
        result.setLastName(user.getLastName());
        result.setCreatedAt(user.getCreatedAt());
        result.setId(user.getId());
        result.setEmail(user.getEmail());
        result.setDescription(user.getDescription());
        result.setFaculty(user.getFaculty());
        result.setType(user.getType());
        result.setActiveProject(user.getActiveProject());
        result.setLikedProjects(user.getLikedProjects());
        result.setProjectInvites(user.getProjectInvites());
        result.setSentApplications(user.getSentApplications());
        result.setTags((user.getTags()));
        return result;
    }
}
