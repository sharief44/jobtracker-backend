package com.sharief.jobtracker.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.sharief.jobtracker.entity.Role;
import com.sharief.jobtracker.entity.User;
import com.sharief.jobtracker.repository.UserRepository;
import com.sharief.jobtracker.security.JwtUtil;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    
    
    public UserService(UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {
this.userRepository = userRepository;
this.passwordEncoder = passwordEncoder;
this.jwtUtil=jwtUtil;
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
    
    public String loginUser(String email, String password) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        return jwtUtil.generateToken(user.getEmail());    
        }
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}