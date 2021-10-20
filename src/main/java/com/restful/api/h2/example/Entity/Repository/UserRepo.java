package com.restful.api.h2.example.Entity.Repository;

import com.restful.api.h2.example.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepo extends CrudRepository<User,Long> {

    public User findByEmail(String email);
    public boolean existsByEmail(String email);
}
