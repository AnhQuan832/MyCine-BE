package com.example.MyCine.Service;


import com.example.MyCine.DTO.AuthenticationDTO;
import com.example.MyCine.DTO.AuthenticationResponseDTO;
import com.example.MyCine.DTO.ResponseMessageDTO;
import com.example.MyCine.DTO.UserRegisterDTO;
import com.example.MyCine.Model.User;
import com.example.MyCine.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.naming.AuthenticationException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final  UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    private final Logger logger = LoggerFactory.getLogger(UserService.class);


    public ResponseEntity<Object> addUser(UserRegisterDTO regDTO){
        User user = userRepository.findUserByEmail(regDTO.getEmail());
        if (user != null){
            if (!user.isLocked()) {
                logger.error("Register user failed: " + regDTO.getEmail() + "EXISTED");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                        .message("Email is already exist")
                        .build());
            }
            else {
                logger.error("Register user failed: " + regDTO.getEmail() + "NON_APPROVED");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                        .message("Please verify your email to continued")
                        .build());
            }
        }
        user = new User(regDTO);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userRepository.insert(user);
        }
        catch (Exception e){
            logger.error("Register user failed: " + regDTO.getEmail() + "EXISTED");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                    .message("Error occurs when adding to database")
                    .build());
        }
        logger.trace("Successfully register user: " + regDTO.getEmail());
        return ResponseEntity.status(HttpStatus.CREATED).body(ResponseMessageDTO.builder()
                .message("Account created")
                .build());
    }




}
