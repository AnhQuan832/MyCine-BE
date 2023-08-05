package com.example.MyCine.Service;

import com.example.MyCine.DTO.AuthenticationDTO;
import com.example.MyCine.DTO.AuthenticationResponseDTO;
import com.example.MyCine.DTO.ResponseMessageDTO;
import com.example.MyCine.Model.User;
import com.example.MyCine.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.security.core.AuthenticationException;
import java.nio.CharBuffer;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> authenticateUser (@RequestBody AuthenticationDTO request){
            User user = userRepository.findUserByEmail(request.getEmail());
            if(user == null)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( ResponseMessageDTO.builder()
                        .message("Your account is not exist")
                        .build());
            if (!passwordEncoder.matches(CharBuffer.wrap(request.getPassword()), user.getPassword()))
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( ResponseMessageDTO.builder()
                        .message("Invalid password")
                        .build());

            if (user.isLocked()) {
                log.error("Authenticate user failed: " + request.getEmail() + " LOCKED");
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseMessageDTO.builder()
                        .message("Your account is locked")
                        .build());
            }

            var jwtToken = jwtService.generateJwtToken(user);
            log.trace("Successfully authenticate user: " + request.getEmail());
            return ResponseEntity.ok(AuthenticationResponseDTO.builder()
                    .jwtToken(jwtToken)
                    .userID(user.getUserID())
                    .email(user.getEmail())
                    .fullName(user.getFullName())
                    .userRoles(user.getUserRoles())
                    .isLocked(user.isLocked())
                    .build());

    }
}
