package com.example.MyCine.Repository;

import com.example.MyCine.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    Optional<User> findUserByEmail(String userEmail);
    Optional<User> findUserByUserID(String userID);
}
