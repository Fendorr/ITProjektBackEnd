package com.restful.api.h2.example.Boundry;


import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Control.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

@RestController
@RequestMapping(path="api/")
public class ProjectController {

    @Autowired
    ProjectService service;

    @GetMapping(path = "project/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable(name="id") Long id){
        try {
            return ResponseEntity.ok(service.getProjectById(id));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping(path = "project/{id}")
    public ResponseEntity<URI> postProject (@PathVariable(name="id") Long userId, @RequestBody ProjectDTO project) throws URISyntaxException {
        try {
            return ResponseEntity.ok(service.postProject(userId,project));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping(path = "project/{id}")
    public ResponseEntity<Void> deleteProject (@PathVariable(name="id") Long id){
        try {
            service.deleteProject(id);
            return ResponseEntity.noContent().build();
        }
        catch(Exception e ) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping(path = "project/{id}")
    public ResponseEntity<Void> updateProject (@PathVariable(name="id") Long id, @RequestBody ProjectDTO project){
        try {
            service.updateProject(id,project);
            return ResponseEntity.ok().build();
        }
        catch(Exception e ) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping(path = "project/{id}")
    public ResponseEntity<Void> changeProjectPhase (@PathVariable(name="id") Long id, @RequestParam Boolean pushToNext){
        try{
            service.changePhase(id, pushToNext);
            return ResponseEntity.ok().build();
        }
        catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
