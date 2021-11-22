package com.restful.api.h2.example.Control;


import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Control.mapper.ProjectMapper;
import com.restful.api.h2.example.Entity.Project;
import com.restful.api.h2.example.Entity.Repository.ProjectRepo;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import com.restful.api.h2.example.Entity.User;
import com.restful.api.h2.example.Entity.projectPhase;
import org.apache.tomcat.util.http.fileupload.MultipartStream;
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
    //@Lazy
    InteractionService interService;

    @Autowired
    ProjectMapper projectMapper;

    public URI postProject(Long userId, ProjectDTO project) throws URISyntaxException {
        //Project initialisieren und Private-Phase zuweisen
        Project createdItem = projectMapper.dtoToEntity(project);
        createdItem.setPhase(projectPhase.Private);
        myProjectRepo.save(createdItem);

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
            Optional<Project> projectOptional = myProjectRepo.findById(id);
            if(projectOptional.isPresent()) {
                Project project = projectOptional.get();
                //DELETE ALL APPLICATIONS IN USERS
                //go through all users that applied to the project
                for(Long applicant : project.getProjectApplicants()) {
                    if(myUserRepo.existsById(applicant)) {
                        Optional<User> userOptional = myUserRepo.findById(applicant);
                        if(userOptional.isPresent()) {
                            //if they exist, get their application array and delete the project id
                            User user = userOptional.get();
                            Long[] oldApplications = user.getSentApplications();
                            Long[] newApplications = interService.deleteIndex(oldApplications, id);
                            user.setSentApplications(newApplications);
                            System.out.println("applicants changed");
                            myUserRepo.save(user);
                        }
                        else {
                            System.out.println("User has not been found.");
                            throw new EntityNotFoundException();
                        }
                    }
                }
                //DELETE ALL LIKES IN USERS
                for(Long like : project.getProjectLikes()) {
                    if(myUserRepo.existsById(like)) {
                        Optional<User> userOptional = myUserRepo.findById(like);
                        if(userOptional.isPresent()) {
                            User user = userOptional.get();
                            Long[] oldLikes = user.getLikedProjects();
                            Long[] newLikes = interService.deleteIndex(oldLikes, id);
                            user.setLikedProjects(newLikes);
                            System.out.println("likes changed");
                            myUserRepo.save(user);
                        }
                        else {
                            System.out.println("User has not been found.");
                            throw new EntityNotFoundException();
                        }
                    }
                }
                //DELETE ALL INVITES IN USERS
                for(Long invite : project.getInvitedUsers()) {
                    if(myUserRepo.existsById(invite)) {
                        Optional<User> userOptional = myUserRepo.findById(invite);
                        if(userOptional.isPresent()) {
                            User user = userOptional.get();
                            Long[] oldInvitations = user.getProjectInvites();
                            Long[] newInvitations = interService.deleteIndex(oldInvitations, id);
                            user.setProjectInvites(newInvitations);
                            System.out.println("invites changed");
                            myUserRepo.save(user);
                        }
                        else {
                            System.out.println("User has not been found.");
                            throw new EntityNotFoundException();
                        }
                    }
                }
            }
            //Finally delete the project
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
        // wenn er kein Projekt Findet Exception werfen ( Controller fängt diese dann)
        Project foundProject = myProjectRepo.findById(id).orElseThrow(RuntimeException::new);
        return projectMapper.entityToDto(foundProject);
    }

    public Collection<ProjectDTO> getProjects()  {
        Iterable<Project> foundProjects = myProjectRepo.findAll();
        return StreamSupport.stream(foundProjects.spliterator(), false) //TODO schaut euch fürs Verständniss die Java Streaming API an :)
                .map(project -> projectMapper.entityToDto(project))
                .collect(Collectors.toList());
    }

    public void changePhase(Long id, boolean pushToNext){
        Project projectToPush = myProjectRepo.findById(id).orElseThrow(RuntimeException::new);

        if (pushToNext){
            switch (projectToPush.getPhase()){
                case Private:
                    projectToPush.setPhase(projectPhase.Public);
                    break;
                case Public:
                    projectToPush.setPhase(projectPhase.Acceptance);
                    break;
                case Acceptance:
                    Long[] members = projectToPush.getMembers();
                    Long[] acceptedMembers = projectToPush.getAcceptedMembers();

                    //Arrays vor dem Vergleich sortieren
                    Arrays.sort(members);
                    Arrays.sort(acceptedMembers);

                    if (Arrays.deepEquals(members, acceptedMembers)){
                        projectToPush.setPhase(projectPhase.Active);
                    }
                    else{
                        System.out.println("Not all members accepted");
                        throw new RuntimeException();
                    }
                    break;
                case Active:
                    System.out.println("Cant push project further");
                    throw new RuntimeException();
            }
        }
        else{
            switch (projectToPush.getPhase()){
                case Private:
                    System.out.println("Cant push project further");
                    throw new RuntimeException();
                case Public:
                    projectToPush.setPhase(projectPhase.Private);
                    break;
                case Acceptance:
                    projectToPush.setPhase(projectPhase.Public);
                    break;
                case Active:
                    projectToPush.setPhase(projectPhase.Acceptance);
                    break;
            }
        }

        myProjectRepo.save(projectToPush);
    }
}
