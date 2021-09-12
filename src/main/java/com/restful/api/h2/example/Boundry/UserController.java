package com.restful.api.h2.example.Boundry;


import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping(path="api/")
public class UserController {

    @Autowired
    UserService service;

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

    @PostMapping(path = "user/")
    public ResponseEntity<URI> postUser (@RequestBody UserDTO userDto) throws URISyntaxException {
        try {
            return ResponseEntity.ok(service.postUser(userDto));
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
    @PutMapping(path = "user/{id}")
    public ResponseEntity<Void> updateUser (@PathVariable(name="id") Long id, @RequestBody UserDTO user){
        try {
            service.updateUser(id,user);
            return ResponseEntity.ok().build();
        }
        catch(Exception e ) {
            return ResponseEntity.badRequest().build();
        }
    }
}
