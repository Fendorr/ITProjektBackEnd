package com.restful.api.h2.example.Boundry;


import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.UserService;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import com.restful.api.h2.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping(path="api/")
public class UserController {

    @Autowired
    UserService service;

    @Autowired
    UserRepo myUserRepo;

    @GetMapping(path = "user/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable(name="id") Long id){
        try {
            return ResponseEntity.ok(service.getUserById(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "user/")
    public ResponseEntity<Collection<UserDTO>> getAllUsers(){
        try {
            return ResponseEntity.ok(service.getUsers());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "user/{id}")
    public ResponseEntity<Void> deleteUser (@PathVariable(name="id") Long id){
        try {
            service.deleteUser(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "user/{id}/{pw}")
    public ResponseEntity<Void> updateUser (@PathVariable(name="id") Long id, @RequestBody UserDTO userDto, @PathVariable(name="pw") String pw ){
        try {
            service.updateUser(id,userDto, pw);
            return ResponseEntity.ok().build();
        }
        catch(Exception e ) {
            return ResponseEntity.badRequest().build();
        }
    }
}
