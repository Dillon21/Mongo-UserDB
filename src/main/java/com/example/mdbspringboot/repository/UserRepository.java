package com.example.mdbspringboot.repository;

import com.example.mdbspringboot.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {

    @Query("{username:?0}")
    User findUserByUsername(String username);

    @Query("{email:?0}")
    User findUserByEmail(String email);

    @Query(value="{id:{$lt:?0}", fields="{id : 1 username : 1, password : 1, email : 1, name : 1}")
    List<User> findAllUsers(String category);

    public long count();

}

