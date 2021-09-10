package com.m2i.demomedical.repository;

import com.m2i.demomedical.entities.UserEntity;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<UserEntity, Integer> {

    public UserEntity findByEmailOrUsername(String username, String email);
    /* SELECT * FROM user WHERE email = :email OR username = :username */
}
