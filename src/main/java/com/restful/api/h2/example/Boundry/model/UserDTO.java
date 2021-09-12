package com.restful.api.h2.example.Boundry.model;



import com.restful.api.h2.example.Entity.userType;
import java.util.Date;


public class UserDTO {

    Long id;

    String firstName;
    String lastName;
    Date createdAt;
    String email;
    userType type;

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
    //endregion
}
