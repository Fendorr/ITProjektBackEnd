package com.restful.api.h2.example.Boundry;


import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.InteractionService;
import com.restful.api.h2.example.Control.ProjectService;
import com.restful.api.h2.example.Control.UserService;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@RestController
@RequestMapping(path="api/")
public class InteractionController {

    @Autowired
    InteractionService service;

    @PutMapping(path = "likeProject/{id}")
    public ResponseEntity<Void> likeProject(@RequestBody Long userId,@PathVariable(name="id") Long projectId){
        try {
            service.likeProject(userId, projectId);
            return ResponseEntity.ok().build();
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }

    }

    @PutMapping(path = "addMember/{id}")
    public ResponseEntity<Void> addMember (@PathVariable(name="id") Long userId, @RequestBody Long projectId){
        try {
            service.addMember(userId,projectId);
            return ResponseEntity.ok().build();
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "applyToProject/{id}")
    public ResponseEntity<Void> applyToProject (@PathVariable(name="id") Long userId, @RequestBody Long projectId){
        try {
            service.applyToProject(userId,projectId);
            return ResponseEntity.ok().build();
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "inviteUser/{id}")
    public ResponseEntity<Void> inviteUser (@PathVariable(name="id") Long userId, @RequestBody Long projectId){
        try {
            service.inviteUser(userId,projectId);
            return ResponseEntity.ok().build();
        }
        catch(Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "acceptProjectAndUpdateAcceptedMembers/projectId/{projectId}/userId/{userId}")
    public ResponseEntity<Void> acceptProjectAndUpdateAcceptedMembers(@PathVariable(name="projectId") Long projectId, @PathVariable(name="userId") Long userId){
        try{
            service.acceptProjectAndUpdateAcceptedMembers(projectId,userId);
            return  ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
