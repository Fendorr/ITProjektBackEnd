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
        return result;
    }
}
