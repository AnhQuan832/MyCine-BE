package com.example.MyCine.Service;


import com.example.MyCine.DTO.UserRegisterDTO;
import com.example.MyCine.Model.User;
import com.example.MyCine.Repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.model.relational.Database;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final Logger logger = LoggerFactory.getLogger(Database.class);
    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean addUser(UserRegisterDTO regDTO){
        User user = new User(regDTO);

        logger.info("user reg dto: " + user);
        logger.trace("User information has been added to database: " + user);
        try{
            userRepository.insert(user);
        }
        catch (Exception e){
            logger.error("There is an error occur while inserting user to database: " + user);
            return false;
        }
        return true;


    }
}
