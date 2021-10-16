package com.restful.api.h2.example.Entity.Repository;

import com.restful.api.h2.example.Entity.Project;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepo extends CrudRepository<Project,Long> {

}
