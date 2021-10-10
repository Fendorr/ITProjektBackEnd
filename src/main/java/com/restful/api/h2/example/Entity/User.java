package com.restful.api.h2.example.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class User {

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

    public void setEmail(String eMail) {
        this.email = email;
    }

    public String getPassword() {
        return password;
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
    //endregion
}
