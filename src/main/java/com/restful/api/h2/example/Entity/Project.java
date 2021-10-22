package com.restful.api.h2.example.Entity;



import io.swagger.v3.oas.annotations.media.Content;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import java.util.Date;

@Entity
public class Project {

    @Id
    @GeneratedValue
    Long id;

    String title;
    String subTitle;
    Date createdAt;
    String createdBy;

    @Column(length=5000)
    String comment;

    String note;
    Long adminId;       //UserID des Admins
    Long professorId;
    String[] tags;      //Keywords
    Integer maxUser;    //Maximale Mitglieder
    Integer currUser;   //Mitglieder aktuell
    Long[] members;     //Array der Member user_ids
    Long[] projectLikes; //Array der user_ids die das Projekt geliked haben (evtl auch später matching algo -> unterscheidung durch 0 oder 1 vor die Id setzen oder so)
    Long[] projectApplicants; //Array der user_ids die sich direkt auf das Projekt beworben haben


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public void setSubTitle(String subTitle) {
        this.subTitle = subTitle;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public Integer getMaxUser() {
        return maxUser;
    }

    public void setMaxUser(Integer maxUser) {
        this.maxUser = maxUser;
    }

    public Integer getCurrUser() {
        return currUser;
    }

    public void setCurrUser(Integer currUser) {
        this.currUser = currUser;
    }

    public Long getAdminId() {
        return adminId;
    }

    public void setAdminId(Long adminId) {
        this.adminId = adminId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public Long[] getMembers() {
        return members;
    }

    public void setMembers(Long[] members) {
        this.members = members;
    }

    public Long[] getProjectLikes() {
        return projectLikes;
    }

    public void setProjectLikes(Long[] projectLikes) {
        this.projectLikes = projectLikes;
    }

    public Long[] getProjectApplicants() {
        return projectApplicants;
    }

    public void setProjectApplicants(Long[] projectApplicants) {
        this.projectApplicants = projectApplicants;
    }
}
