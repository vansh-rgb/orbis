package com.strink.orbis.service;

import com.strink.orbis.dto.UserCredDto;
import com.strink.orbis.model.User;
import com.strink.orbis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authManager;

    public User registerUser(UserCredDto registerUserDto) {
        User user = new User();

        user.setUsername(registerUserDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerUserDto.getPassword()));
        user.setCreatedAt(new Date(System.currentTimeMillis()));

        return userRepository.save(user);
    }

    public User authenticate(UserCredDto loginUserDto ) {
        authManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword()));

        return userRepository.findByUsername(loginUserDto.getUsername()).orElseThrow();
    }

}
