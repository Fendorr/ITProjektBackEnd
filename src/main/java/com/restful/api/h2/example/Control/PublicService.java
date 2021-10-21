package com.restful.api.h2.example.Control;

import com.restful.api.h2.example.Boundry.model.LoginDTO;
import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.mapper.UserMapper;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import com.restful.api.h2.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PublicService {

    @Autowired
    UserRepo myUserRepo;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean login(LoginDTO loginDto){
        User foundUser = myUserRepo.findByEmail(loginDto.getEmail());
        if (foundUser == null){
            throw new RuntimeException();
        }
        return passwordEncoder.matches(loginDto.getPassword(), foundUser.getPassword());
    }

    public UserDTO getCurrentUser(){
        //get authenticated User
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();

        //map to DTO
        return userMapper.entityToDto(user);
    }
}
