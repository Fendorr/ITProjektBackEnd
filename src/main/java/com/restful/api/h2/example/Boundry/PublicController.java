package com.restful.api.h2.example.Boundry;

import com.restful.api.h2.example.Boundry.model.LoginDTO;
import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.ProjectService;
import com.restful.api.h2.example.Control.PublicService;
import com.restful.api.h2.example.Control.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.Collection;

@RestController
@RequestMapping(path="api/")
public class PublicController {

    @Autowired
    @Lazy
    PublicService publicService;

    @Autowired
    @Lazy
    UserService userService;

    @Autowired
    @Lazy
    ProjectService projectService;

    @PostMapping(path = "login/")
    public ResponseEntity<Boolean> login (@RequestBody LoginDTO loginDto) throws URISyntaxException {
        try {
            return ResponseEntity.ok(publicService.login(loginDto));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path="curUser/")
    public ResponseEntity<UserDTO> currentUser() {
        try{
            return ResponseEntity.ok(publicService.getCurrentUser());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "user/{pw}")
    public ResponseEntity<URI> postUser (@RequestBody UserDTO userDto, @PathVariable(name="pw") String pw ) throws URISyntaxException {
        try {
            return ResponseEntity.ok(userService.postUser(userDto, pw));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping(path = "project/")
    public ResponseEntity<Collection<ProjectDTO>> getAllProjects(){
        try {
            return ResponseEntity.ok(projectService.getProjects());
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
