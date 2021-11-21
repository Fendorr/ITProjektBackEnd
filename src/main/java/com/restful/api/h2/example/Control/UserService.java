package com.restful.api.h2.example.Control;


import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.mapper.UserMapper;
import com.restful.api.h2.example.Entity.Project;
import com.restful.api.h2.example.Entity.Repository.ProjectRepo;
import com.restful.api.h2.example.Entity.User;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import org.apache.catalina.util.ToStringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    UserRepo myUserRepo;

    @Autowired
    ProjectRepo myProjectRepo;

    @Autowired
    UserMapper userMapper;

    @Autowired
    PasswordEncoder passwordEncoder;

    public URI postUser(UserDTO userDto, String password) throws URISyntaxException {

        //Prüfe ob User bereits existiert
        if (myUserRepo.existsByEmail(userDto.getEmail())){
            throw new RuntimeException();
        }

        User user = userMapper.dtoToEntity(userDto);
        user.setPassword(passwordEncoder.encode(password)); //passwort explizit hier setzen, sodass es nicht im Dto vorhanden ist
        user.setIsCurrentProjectAccepted(false);
        User createdItem = myUserRepo.save(user);

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

    public void updateUser(Long id, UserDTO userDto, String password) {
        User userToUpdate = userMapper.dtoToEntity(userDto);
        userToUpdate.setId(id);
        userToUpdate.setPassword(passwordEncoder.encode(password)); //passwort explizit hier setzen, sodass es nicht im Dto vorhanden ist
        myUserRepo.save(userToUpdate);
    }

    public UserDTO getUserById(Long id) {
        User foundUser = myUserRepo.findById(id).orElseThrow(RuntimeException::new);
        return userMapper.entityToDto(foundUser);
    }

    public Collection<UserDTO> getUsers()  {
        Iterable<User> foundUsers = myUserRepo.findAll();
        return StreamSupport.stream(foundUsers.spliterator(), false)
                .map(user -> userMapper.entityToDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return myUserRepo.findByEmail(s);
    }
}
