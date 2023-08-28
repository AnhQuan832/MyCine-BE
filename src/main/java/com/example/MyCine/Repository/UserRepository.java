package com.example.MyCine.Repository;

import com.example.MyCine.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UserRepository extends MongoRepository<User,String> {
    User findUserByEmail(String userEmail);
    User findUserByUserID(String userID);
}
