package com.restful.api.h2.example.Control;


import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.mapper.UserMapper;
import com.restful.api.h2.example.Entity.User;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService {

    @Autowired
    UserRepo myUserRepo;

    @Autowired
    UserMapper userMapper;

    public URI postUser(UserDTO userDto) throws URISyntaxException {
        User createdItem = myUserRepo.save(userMapper.dtoToEntity(userDto));
        return new URI("localhost:8080/api/user/" +createdItem.getId());
    }

    public void deleteUser(Long id) {
        if(myUserRepo.existsById(id)) {
            myUserRepo.deleteById(id);
        }
        else {
            throw new RuntimeException();
        }
    }

    public void updateUser(Long id,UserDTO userDto) {
        User userToUpdate = userMapper.dtoToEntity(userDto);
        userToUpdate.setId(id);
        myUserRepo.save(userToUpdate);
    }

    public UserDTO getUserById(Long id) {
        User foundProject = myUserRepo.findById(id).orElseThrow(RuntimeException::new);
        return userMapper.entityToDto(foundProject);
    }

    public Collection<UserDTO> getUsers()  {
        Iterable<User> foundUsers = myUserRepo.findAll();
        return StreamSupport.stream(foundUsers.spliterator(), false)
                .map(user -> userMapper.entityToDto(user))
                .collect(Collectors.toList());
    }
}
