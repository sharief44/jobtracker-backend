package com.sharief.jobtracker.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sharief.jobtracker.entity.Role;
import com.sharief.jobtracker.entity.User;
import com.sharief.jobtracker.repository.UserRepository;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    
    
    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder) {
this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
}

    public User registerUser(User user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setRole(Role.USER);
        
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }
}