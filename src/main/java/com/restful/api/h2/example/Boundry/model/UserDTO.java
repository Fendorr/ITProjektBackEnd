package com.restful.api.h2.example.Boundry.model;



import com.restful.api.h2.example.Entity.userType;
import java.util.Date;


public class UserDTO {

    Long id;

    Date createdAt;
    String firstName;
    String lastName;
    String email;

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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public userType getType() {
        return type;
    }

    public void setType(userType type) {
        this.type = type;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public Long getActiveProject() {
        return activeProject;
    }

    public void setActiveProject(Long activeProject) {
        this.activeProject = activeProject;
    }

    //endregion
}
