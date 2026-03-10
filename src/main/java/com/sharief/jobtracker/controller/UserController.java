package com.sharief.jobtracker.controller;

import java.security.Principal;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sharief.jobtracker.dto.LoginRequest;
import com.sharief.jobtracker.dto.LoginResponse;
import com.sharief.jobtracker.dto.UserRegisterRequest;
import com.sharief.jobtracker.entity.User;
import com.sharief.jobtracker.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public User register(@Valid @RequestBody UserRegisterRequest request) {
        return userService.registerUser(request);
    }
    
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return userService.loginUser(request.getEmail(), request.getPassword());
    }
    
    
    @GetMapping("/me")
    public User getCurrentUser(Principal principal) {

        return userService.getUserByEmail(principal.getName());

    }
    }
    
   
