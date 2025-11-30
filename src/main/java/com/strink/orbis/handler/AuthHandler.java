package com.strink.orbis.handler;

import com.strink.orbis.dto.AuthResponseDto;
import com.strink.orbis.dto.UserCredDto;
import com.strink.orbis.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthHandler {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<Object> registerUser(@RequestBody UserCredDto registerUserDto) {
        AuthResponseDto authResponse = userService.registerUser(registerUserDto, true);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }

    @GetMapping("/login")
    public ResponseEntity<Object> loginUser(@RequestBody UserCredDto loginUserDto) {
        AuthResponseDto authResponse = userService.loginUser(loginUserDto, false);
        return new ResponseEntity<>(authResponse, HttpStatus.ACCEPTED);
    }
}
