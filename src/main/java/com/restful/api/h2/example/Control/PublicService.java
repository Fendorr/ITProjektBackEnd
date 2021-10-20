package com.restful.api.h2.example.Control;

import com.restful.api.h2.example.Boundry.model.LoginDTO;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import com.restful.api.h2.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PublicService {

    @Autowired
    UserRepo myUserRepo;

    @Autowired
    PasswordEncoder passwordEncoder;

    public boolean login(LoginDTO loginDto){
        User foundUser = myUserRepo.findByEmail(loginDto.getEmail());
        if (foundUser == null){
            throw new RuntimeException();
        }
        return passwordEncoder.matches(loginDto.getPassword(), foundUser.getPassword());
    }
}
