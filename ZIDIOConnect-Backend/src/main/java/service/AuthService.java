package com.zidioconnect.service;

import com.zidioconnect.model.User;
import com.zidioconnect.repository.UserRepository;
import com.zidioconnect.dto.RegisterRequest;
import com.zidioconnect.dto.LoginRequest;
import com.zidioconnect.dto.JwtResponse;
import com.zidioconnect.config.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtUtil jwtUtil;

    public ResponseEntity<String> register(RegisterRequest request) {
        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole());
        userRepository.save(user);
        return ResponseEntity.ok("User registered successfully");
    }

    public ResponseEntity<JwtResponse> login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        if (passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            String token = jwtUtil.generateToken(user.getEmail());
            return ResponseEntity.ok(new JwtResponse(token));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
