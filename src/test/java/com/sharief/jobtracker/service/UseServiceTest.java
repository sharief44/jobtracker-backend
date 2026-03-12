package com.sharief.jobtracker.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.sharief.jobtracker.dto.UserRegisterRequest;
import com.sharief.jobtracker.entity.Role;
import com.sharief.jobtracker.entity.User;
import com.sharief.jobtracker.repository.UserRepository;
import com.sharief.jobtracker.service.UserService;

@SpringBootTest
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void shouldRegisterUserSuccessfully() {

        UserRegisterRequest request = new UserRegisterRequest();
        request.setName("Sharief");
        request.setEmail("sharief@test.com");
        request.setPassword("Password123");

        when(userRepository.findByEmail(request.getEmail()))
                .thenReturn(Optional.empty());

        when(passwordEncoder.encode(request.getPassword()))
                .thenReturn("encodedPassword");

        User savedUser = new User();
        savedUser.setName("Sharief");
        savedUser.setEmail("sharief@test.com");
        savedUser.setRole(Role.USER);

        when(userRepository.save(org.mockito.Mockito.any(User.class)))
                .thenReturn(savedUser);

        User result = userService.registerUser(request);

        assertEquals("Sharief", result.getName());
        assertEquals("sharief@test.com", result.getEmail());
        assertEquals(Role.USER, result.getRole());
    }
}