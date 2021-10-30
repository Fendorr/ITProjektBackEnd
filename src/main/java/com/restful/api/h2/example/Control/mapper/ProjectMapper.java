package com.restful.api.h2.example.Control.mapper;


import com.restful.api.h2.example.Boundry.model.ProjectDTO;
import com.restful.api.h2.example.Entity.Project;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class ProjectMapper {

    public Project dtoToEntity(ProjectDTO projDto){
        Project result = new Project();
        result.setComment(projDto.getComment());
        result.setCreatedAt(new Date());
        result.setNote(projDto.getNote());
        result.setTitle(projDto.getTitle());
        result.setCreatedBy(projDto.getCreatedBy());
        result.setSubTitle(projDto.getSubTitle());
        result.setTags(projDto.getTags());
        result.setMaxUser(projDto.getMaxUser());
        result.setCurrUser(projDto.getCurrUser());
        result.setAdminId(projDto.getAdminId());
        result.setProfessorId(projDto.getProfessorId());
        result.setMembers(projDto.getMembers());
        result.setProjectLikes(projDto.getProjectLikes());
        result.setProjectApplicants(projDto.getProjectApplicants());
        result.setInvitedUsers(projDto.getInvitedUsers());
        result.setChat(projDto.getChat());
        return result;
    }

    public ProjectDTO entityToDto(Project proj){
        ProjectDTO result = new ProjectDTO();
        result.setComment(proj.getComment());
        result.setCreatedAt(proj.getCreatedAt());
        result.setNote(proj.getNote());
        result.setTitle(proj.getTitle());
        result.setCreatedBy(proj.getCreatedBy());
        result.setSubTitle(proj.getSubTitle());
        result.setId(proj.getId());
        result.setTags(proj.getTags());
        result.setMaxUser(proj.getMaxUser());
        result.setCurrUser(proj.getCurrUser());
        result.setAdminId(proj.getAdminId());
        result.setProfessorId(proj.getProfessorId());
        result.setMembers(proj.getMembers());
        result.setProjectLikes(proj.getProjectLikes());
        result.setProjectApplicants(proj.getProjectApplicants());
        result.setInvitedUsers(proj.getInvitedUsers());
        result.setChat(proj.getChat());
        return result;
    }
}
