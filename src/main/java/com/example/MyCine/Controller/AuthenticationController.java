package com.example.MyCine.Controller;

import com.example.MyCine.DTO.AuthenticationDTO;
import com.example.MyCine.DTO.UserRegisterDTO;
import com.example.MyCine.Service.AuthenticationService;
import com.example.MyCine.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@CrossOrigin
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationService authService;

    @PostMapping("/register")
    public Object userRegister(@RequestBody UserRegisterDTO request){
        return userService.addUser(request);
    }

    @PostMapping("/login")
    public Object authenticate(@RequestBody AuthenticationDTO request) {
        return authService.authenticateUser(request);
    }
    @GetMapping("/test")
    public String test(){
        return "OK";
    }
}
