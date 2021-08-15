package com.restful.api.h2.example.Entity.Repository;

import com.restful.api.h2.example.Entity.Project;
import org.springframework.data.repository.CrudRepository;

public interface ProjectRepo extends CrudRepository<Project,Long> {

}
