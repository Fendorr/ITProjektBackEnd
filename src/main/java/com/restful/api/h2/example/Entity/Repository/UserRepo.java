package com.restful.api.h2.example.Entity.Repository;

import com.restful.api.h2.example.Entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserRepo extends CrudRepository<User,Long> {

}
