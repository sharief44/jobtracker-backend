package com.sharief.jobtracker.service;

import com.sharief.jobtracker.entity.Role;
import com.sharief.jobtracker.entity.User;
import com.sharief.jobtracker.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerUser(User user) {

        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setRole(Role.USER);

        return userRepository.save(user);
    }
}