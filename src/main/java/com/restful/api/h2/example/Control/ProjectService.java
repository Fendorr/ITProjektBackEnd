package com.restful.api.h2.example.Control;


import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Control.mapper.ProjectMapper;
import com.restful.api.h2.example.Entity.Project;
import com.restful.api.h2.example.Entity.Repository.ProjectRepo;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import com.restful.api.h2.example.Entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class ProjectService {

    @Autowired
    ProjectRepo myProjectRepo;

    @Autowired
    //@Lazy
    UserRepo myUserRepo;

    @Autowired
    ProjectMapper projectMapper;

    public URI postProject(Long userId, ProjectDTO project) throws URISyntaxException {
        Project createdItem = myProjectRepo.save(projectMapper.dtoToEntity(project));
        if(myUserRepo.existsById(userId)) {
            Optional<User> userOptional = myUserRepo.findById(userId);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                user.setActiveProject(createdItem.getId());
                myUserRepo.save(user);
                System.out.println("user active project saved");
            }
            else {
                System.out.println("User has not been found.");
                throw new EntityNotFoundException();
            }
        }
        return new URI("localhost:8080/api/project/" +createdItem.getId());
    }

    public void deleteProject(Long id) {
        if(myProjectRepo.existsById(id)) {
            myProjectRepo.deleteById(id);
        }
        else {
            throw new RuntimeException();
        }
    }

    public void updateProject(Long id,ProjectDTO project) {
        Project projectToUpdate = projectMapper.dtoToEntity(project);
        projectToUpdate.setId(id); // Durch setzen der Id weiß das Repo welcher Eintrag upgedated werden soll
        myProjectRepo.save(projectToUpdate);
    }

    public ProjectDTO getProjectById(Long id) {
        // wemm er kein Projekt Findet Exception werfen ( Controller fängt diese dann)
        Project foundProject = myProjectRepo.findById(id).orElseThrow(RuntimeException::new);
        return projectMapper.entityToDto(foundProject);
    }

    public Collection<ProjectDTO> getProjects()  {
        Iterable<Project> foundProjects = myProjectRepo.findAll();
        return StreamSupport.stream(foundProjects.spliterator(), false) //TODO schaut euch fürs Verständniss die Java Streaming API an :)
                .map(project -> projectMapper.entityToDto(project))
                .collect(Collectors.toList());
    }
}
