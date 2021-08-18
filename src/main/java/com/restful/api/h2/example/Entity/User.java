package com.restful.api.h2.example.Entity;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Date;

enum userType {
    student, professor, admin
}

@Entity
public class User {

    @Id
    @GeneratedValue
    Long id;

    String firstName;
    String lastName;
    String eMail;
    String password; //! pw muss gehashed und gesalted sein -> wsl im DTO
    String faculty;
    Date createdAt;
    userType type; //! s = student, p = professor, a = admin
    Long activeProject; //! -> Fremdschlüssel project_id

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
    //endregion
}
