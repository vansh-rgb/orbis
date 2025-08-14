package com.strink.orbis.handler;


import com.strink.orbis.dto.LoginUserDto;
import com.strink.orbis.dto.RegisterUserDto;
import com.strink.orbis.dto.UserCredDto;
import com.strink.orbis.model.User;
import com.strink.orbis.service.AuthService;
import com.strink.orbis.service.JwtService;
import com.strink.orbis.service.UserService;
import lombok.Getter;
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
    public ResponseEntity<String> registerUser(@RequestBody UserCredDto registerUserDto) {
        try {
            String jwtToken = userService.registerUser(registerUserDto, true);
            return new ResponseEntity<>(jwtToken, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/login")
    public ResponseEntity<String> loginUser(@RequestBody UserCredDto loginUserDto) throws Exception {
        try {
            String jwtToken = userService.loginUser(loginUserDto, false);
            return new ResponseEntity<>(jwtToken, HttpStatus.ACCEPTED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
