package com.example.MyCine.Controller;

import com.example.MyCine.DTO.UserRegisterDTO;
import com.example.MyCine.Service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private final UserService userService;


    @PostMapping("/register")
    public Object userRegister(@RequestBody UserRegisterDTO request){
        return userService.addUser(request);
    }
    @GetMapping("/test")
    public String test(){
        return "OK";
    }
}
