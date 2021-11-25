package com.restful.api.h2.example.Control;


import com.restful.api.h2.example.Boundry.model.UserDTO;
import com.restful.api.h2.example.Control.mapper.ProjectMapper;
import com.restful.api.h2.example.Control.mapper.UserMapper;
import com.restful.api.h2.example.Entity.Project;
import com.restful.api.h2.example.Entity.Repository.ProjectRepo;
import com.restful.api.h2.example.Entity.Repository.UserRepo;
import com.restful.api.h2.example.Entity.User;
import com.restful.api.h2.example.Entity.projectPhase;
import com.restful.api.h2.example.Entity.userType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.persistence.EntityNotFoundException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class InteractionService {

    @Autowired
    @Lazy
    UserRepo myUserRepo;

    @Autowired
    @Lazy
    ProjectRepo myProjectRepo;

    @Autowired
    @Lazy
    ProjectService projectService;

    @Autowired
    @Lazy
    UserMapper userMapper;

    //This method is called when a User likes a project
    public void likeProject(Long userId, Long projectId){

        if(myUserRepo.existsById(userId)) {
            Optional<User> userOptional = myUserRepo.findById(userId);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                //Set the new likedProjects array
                Long[] projects = user.getLikedProjects();
                //Check if project is already liked if true: unlike
                boolean contains = false;
                for(Long element : projects) {
                    if(element.equals(projectId)) {
                        user.setLikedProjects(deleteIndex(projects, element));
                        myUserRepo.save(user);
                        System.out.println("Element has been deleted from Liked Projects");
                        contains = true;
                    }
                }
                if(!contains) {
                    Long[] newProjects = Arrays.copyOf(projects, projects.length + 1);
                    newProjects[newProjects.length - 1] = projectId;
                    user.setLikedProjects(newProjects);
                    myUserRepo.save(user);
                    System.out.println("User " + userId + " has been updated");
                }
            }
            else {
                System.out.println("User has not been found.");
                throw new EntityNotFoundException();
            }
        }
        if(myProjectRepo.existsById(projectId)) {
            Optional<Project> projectOptional = myProjectRepo.findById(projectId);
            if(projectOptional.isPresent()) {
                Project project = projectOptional.get();
                //Add the User to the project likes
                Long[] projectLikes = project.getProjectLikes();

                //Check if project is already liked if true: unlike
                boolean projContains = false;
                for(Long element : projectLikes) {
                    if(element.equals(userId)) {
                        project.setProjectLikes(deleteIndex(projectLikes, element));
                        myProjectRepo.save(project);
                        System.out.println("Element has been deleted from Project Likes");
                        projContains = true;
                    }
                }
                if(!projContains) {
                    Long[] newProjectLikes = Arrays.copyOf(projectLikes, projectLikes.length + 1);
                    newProjectLikes[newProjectLikes.length - 1] = userId;
                    project.setProjectLikes(newProjectLikes);
                    myProjectRepo.save(project);
                    System.out.println("Project " + projectId + " has been updated");
                }
            }
            else {
                System.out.println("Project has not been found.");
                throw new EntityNotFoundException();
            }
        }
    }

    //This method is called if an admin adds/removes a member to/from a project
    public void addMember(Long userId, Long projectId) {
        if (myProjectRepo.existsById(projectId)) {
            Optional<Project> projectOptional = myProjectRepo.findById(projectId);
            if (projectOptional.isPresent()) {
                Project project = projectOptional.get();
                Long[] members = project.getMembers();

                //Check if user is already member, yes: remove him
                boolean projContains = false;
                for (Long element : members) {
                    //Only delete if user IS NOT ADMIN!!!
                    if (element.equals(userId) || userId.equals(project.getProfessorId()) && !userId.equals(project.getAdminId())) {
                        if(userId.equals(project.getProfessorId())) {
                            project.setProfessorId(null);
                        }
                        else {
                            Long[] newMembers = deleteIndex(members, element);
                            project.setMembers(newMembers);
                            project.setCurrUser(newMembers.length);
                        }
                        myProjectRepo.save(project);
                        System.out.println("User has been removed from project members");
                        //Delete project from users' activeProject
                        if (myUserRepo.existsById(userId)) {
                            Optional<User> userOptional = myUserRepo.findById(userId);
                            if (userOptional.isPresent()) {
                                User user = userOptional.get();
                                user.setActiveProject(null);
                                myUserRepo.save(user);
                                System.out.println("Users activeProject has been changed");
                            } else {
                                System.out.println("User has not been found.");
                                throw new EntityNotFoundException();
                            }
                        }
                        projContains = true;
                    }
                    //IF ADMIN LEAVES PROJECT EITHER MAKE SOMEBODY ELSE ADMIN OR DELETE PROJECT
                    if(element.equals(userId) && userId.equals(project.getAdminId())) {
                        Long[] newMembers = deleteIndex(members, element);
                        if(newMembers.length < 1){
                            //delete project if newMembers is empty after admin leave
                            // myProjectRepo.delete(project);
                            projectService.deleteProject(project.getId());

                            System.out.println("Project has been deleted because it is empty");
                        } else {
                            project.setMembers(newMembers);
                            project.setCurrUser(newMembers.length);
                            //set new admin
                            project.setAdminId(newMembers[0]);
                            myProjectRepo.save(project);
                            System.out.println("Admin has been removed from project");
                        }
                        //Delete project from old admins' activeProject
                        if (myUserRepo.existsById(userId)) {
                            Optional<User> userOptional = myUserRepo.findById(userId);
                            if (userOptional.isPresent()) {
                                User user = userOptional.get();
                                user.setActiveProject(null);
                                myUserRepo.save(user);
                                System.out.println("Users activeProject has been changed");
                            } else {
                                System.out.println("User has not been found.");
                                throw new EntityNotFoundException();
                            }
                        }
                        projContains = true;
                    }
                }
                if (!projContains && !userId.equals(project.getAdminId())) {
                    //Check if project is already at capacity
                    if(project.getCurrUser() >= project.getMaxUser()){
                        System.out.println("Project is already full");
                    }
                    else {
                        if(myUserRepo.existsById(userId)) {
                            Optional<User> userOpt = myUserRepo.findById(userId);
                            if (userOpt.isPresent()) {
                                User user = userOpt.get();

                                if(user.getType() == userType.Professor) {
                                    project.setProfessorId(userId);
                                }
                                else {
                                    Long[] newMembers = Arrays.copyOf(members, members.length + 1);
                                    newMembers[newMembers.length - 1] = userId;
                                    project.setMembers(newMembers);
                                    project.setCurrUser(newMembers.length);
                                    myProjectRepo.save(project);
                                    System.out.println("User" + userId + " has been added to project members");
                                }
                            }
                        }

                        //Add project to users' activeProject
                        if (myUserRepo.existsById(userId)) {
                            Optional<User> userOptional = myUserRepo.findById(userId);
                            if (userOptional.isPresent()) {
                                User user = userOptional.get();
                                user.setActiveProject(projectId);

                                //DELETE ALL APPLICATIONS SENT BY THE USER
                                for (Long application : user.getSentApplications()) {
                                    applyToProject(userId,application);
                                }
                                //DELETE ALL INVITATIONS RECEIVED BY THE USER
                                for (Long invitation : user.getProjectInvites()) {
                                    inviteUser(userId,invitation);
                                }

                                myUserRepo.save(user);
                                System.out.println("Users activeProject has been changed");
                            } else {
                                System.out.println("User has not been found.");
                                throw new EntityNotFoundException();
                            }
                        }
                    }
                }
            } else {
                System.out.println("Project has not been found.");
                throw new EntityNotFoundException();
            }
        }
    }

    //This method is called when a User applies to a Project
    public void applyToProject(Long userId, Long projectId){
        if(myUserRepo.existsById(userId)) {
            Optional<User> userOptional = myUserRepo.findById(userId);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                //Set the new Applications
                Long[] applications = user.getSentApplications();
                //Check if user already applied
                boolean contains = false;
                for(Long element : applications) {
                    if(element.equals(projectId)) {
                        user.setSentApplications(deleteIndex(applications, element));
                        myUserRepo.save(user);
                        System.out.println("Element has been deleted from Applications");
                        contains = true;
                    }
                }
                if(!contains) {
                    Long[] newApplications = Arrays.copyOf(applications, applications.length + 1);
                    newApplications[newApplications.length - 1] = projectId;
                    user.setSentApplications(newApplications);
                    myUserRepo.save(user);
                    System.out.println("User " + userId + "Applications have been updated");
                }
            }
            else {
                System.out.println("User has not been found.");
                throw new EntityNotFoundException();
            }
        }
        if(myProjectRepo.existsById(projectId)) {
            Optional<Project> projectOptional = myProjectRepo.findById(projectId);
            if(projectOptional.isPresent()) {
                Project project = projectOptional.get();
                //Add the User to projectApplicants
                Long[] projectApplicants = project.getProjectApplicants();

                //Check if project is already applying if yes, remove him ("take back application")
                boolean projContains = false;
                for(Long element : projectApplicants) {
                    if(element.equals(userId)) {
                        project.setProjectApplicants(deleteIndex(projectApplicants, element));
                        myProjectRepo.save(project);
                        System.out.println("User no longer applying to project");
                        projContains = true;
                    }
                }
                if(!projContains) {
                    Long[] newProjectApplicants = Arrays.copyOf(projectApplicants, projectApplicants.length + 1);
                    newProjectApplicants[newProjectApplicants.length - 1] = userId;
                    project.setProjectApplicants(newProjectApplicants);
                    myProjectRepo.save(project);
                    System.out.println("Project" + projectId + "Applicants have been updated");
                }
            }
            else {
                System.out.println("Project has not been found.");
                throw new EntityNotFoundException();
            }
        }
    }

    //This method is called when a projectAdmin invites a User
    public void inviteUser(Long userId, Long projectId){
        if(myUserRepo.existsById(userId)) {
            Optional<User> userOptional = myUserRepo.findById(userId);
            if(userOptional.isPresent()) {
                User user = userOptional.get();
                //Set the new userInvites
                Long[] invites = user.getProjectInvites();
                //Check if user is already invited
                boolean contains = false;
                for(Long element : invites) {
                    if(element.equals(projectId)) {
                        user.setProjectInvites(deleteIndex(invites, element));
                        myUserRepo.save(user);
                        System.out.println("Element has been deleted from Invites");
                        contains = true;
                    }
                }
                if(!contains) {
                    Long[] newInvites = Arrays.copyOf(invites, invites.length + 1);
                    newInvites[newInvites.length - 1] = projectId;
                    user.setProjectInvites(newInvites);
                    myUserRepo.save(user);
                    System.out.println("User " + userId + "Invites have been updated");
                }
            }
            else {
                System.out.println("User has not been found.");
                throw new EntityNotFoundException();
            }
        }
        if(myProjectRepo.existsById(projectId)) {
            Optional<Project> projectOptional = myProjectRepo.findById(projectId);
            if(projectOptional.isPresent()) {
                Project project = projectOptional.get();
                //Add the User to the InvitedUsers
                Long[] projectInvites = project.getInvitedUsers();

                //Check if user is already invited
                boolean projContains = false;
                for(Long element : projectInvites) {
                    if(element.equals(userId)) {
                        project.setInvitedUsers(deleteIndex(projectInvites, element));
                        myProjectRepo.save(project);
                        System.out.println("Element has been deleted from ProjectInvites");
                        projContains = true;
                    }
                }
                if(!projContains) {
                    Long[] newProjectInvites = Arrays.copyOf(projectInvites, projectInvites.length + 1);
                    newProjectInvites[newProjectInvites.length - 1] = userId;
                    project.setInvitedUsers(newProjectInvites);
                    myProjectRepo.save(project);
                    System.out.println("Project " + projectId + "Invites have been updated");
                }
            }
            else {
                System.out.println("Project has not been found.");
                throw new EntityNotFoundException();
            }
        }
    }

    public void acceptProjectAndUpdateAcceptedMembers(Long projectId, Long userId){
        Project projectToAccept = myProjectRepo.findById(projectId).orElseThrow(RuntimeException::new);
        User userToAdd = myUserRepo.findById(userId).orElseThrow(RuntimeException::new);

        if (projectToAccept.getPhase() != projectPhase.Acceptance){
            System.out.println("Project is not in acceptance phase yet");
            throw new RuntimeException();
        }
        if (userToAdd.getActiveProject().longValue() != projectId){
            System.out.println("User is not member of this project");
            throw new RuntimeException();
        }
        else {
            try{
                Long[] acceptedMembers = projectToAccept.getAcceptedMembers();

                //Initialisiere Long[] als Liste und füge User mit userId hinzu
                List<Long> acceptedMembersAsList = new ArrayList<>(Arrays.asList(acceptedMembers));
                if (acceptedMembersAsList.contains(userId)){
                    System.out.println("User already accepted");
                    throw new RuntimeException();
                }
                acceptedMembersAsList.add(userId);

                //Transformiere zurück zu Long[]
                Long[] newAcceptedMembers = new Long[acceptedMembersAsList.size()];
                acceptedMembers = acceptedMembersAsList.toArray(newAcceptedMembers);

                projectToAccept.setAcceptedMembers(acceptedMembers);
                myProjectRepo.save(projectToAccept);

            }
            catch (Exception e){
                //Initialisiere neuen leeren Long[] Array
                Long[] acceptedMembers = new Long[1];
                acceptedMembers[0] = userId;

                projectToAccept.setAcceptedMembers(acceptedMembers);
                myProjectRepo.save(projectToAccept);
            }
        }
    }

    //Helper method, to delete an index from a <Long> Array.
    public Long[] deleteIndex(Long[] arr, Long element){
        //find index of element to delete
        int index = -1;
        for (int i = 0; i < arr.length; i++)
        {
            if (arr[i].equals(element)) {
                index = i;
            }
        }
        //Check if deletion is possible else return empty array
        if (index < 0) {
            return new Long[]{};
        } else {
            //Create new Array that's smaller
            Long[] newArr = new Long[arr.length - 1];
            //Copy old array until index
            System.arraycopy(arr, 0, newArr, 0, index);
            //Copy old array after index
            System.arraycopy(arr, index+1, newArr, index, arr.length - index - 1);
            return newArr;
        }

    }
}
