package com.restful.api.h2.example.Entity;


import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Entity
public class User implements UserDetails {

    @Id
    @GeneratedValue
    Long id;

    Date createdAt;
    String firstName;
    String lastName;
    String email;

    String password;        //! pw muss gehashed und gesalted sein -> wsl im DTO
    String faculty;

    userType type;          //! s = student, p = professor
    Long activeProject;     //! -> Fremdschlüssel project_id
    Long[] likedProjects;
    Long[] projectInvites; //If user is invited to a projects
    Long[] sentApplications; //All applications users have sent
    Boolean isCurrentProjectAccepted;

    //region Getter/Setter
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public userType getType() {
        return type;
    }

    public void setType(userType type) {
        this.type = type;
    }

    public Long getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(Long activeProject) {
        this.activeProject = activeProject;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long[] getLikedProjects() {
        return likedProjects;
    }

    public void setLikedProjects(Long[] likedProjects) {
        this.likedProjects = likedProjects;
    }

    public Long[] getProjectInvites() {
        return projectInvites;
    }

    public void setProjectInvites(Long[] projectInvites) {
        this.projectInvites = projectInvites;
    }

    public Long[] getSentApplications() {
        return sentApplications;
    }

    public void setSentApplications(Long[] sentApplications) {
        this.sentApplications = sentApplications;
    }

    public Boolean getIsCurrentProjectAccepted() {
        return isCurrentProjectAccepted;
    }

    public void setIsCurrentProjectAccepted(Boolean currentProjectAccepted) {
        isCurrentProjectAccepted = currentProjectAccepted;
    }

    //endregion


    //region Erweiterung UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
    //endregion

}
