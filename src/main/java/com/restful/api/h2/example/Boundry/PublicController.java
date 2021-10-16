package com.restful.api.h2.example.Boundry;

import com.restful.api.h2.example.Boundry.model.LoginDTO;
import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Control.PublicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping(path="api/")
public class PublicController {

    @Autowired
    @Lazy
    PublicService service;

    @PostMapping(path = "login/")
    public ResponseEntity<URI> login (@RequestBody LoginDTO loginDto) throws URISyntaxException {
        try {
            service.login(loginDto);
            return ResponseEntity.ok().build();
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    //TODO move PostUser und getAllProjects

}
