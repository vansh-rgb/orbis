package com.strink.orbis.service;

import com.strink.orbis.dto.AuthResponseDto;
import com.strink.orbis.dto.UserCredDto;
import com.strink.orbis.dto.UserDetailsDto;
import com.strink.orbis.model.User;
import com.strink.orbis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void validateCredentials(UserCredDto registerUserDto, boolean isRegister) throws Exception {
        validateUsername(registerUserDto.getUsername());
        validatePassword(registerUserDto.getPassword());
        if(isRegister) {
            checkUserInDB(registerUserDto);
        }
    }

    private void checkUserInDB(UserCredDto registerUserDto) throws Exception {
        Optional<User> user = userRepository.findByUsername(registerUserDto.getUsername());
        if(user.isPresent()) {
            throw new Exception("Username already exists");
        }
    }

    private void validateUsername(String username) throws Exception {
        if(username.length() < 6) throw new Exception("Username should be atleast of length 6");
    }

    private void validatePassword(String password) throws Exception{
        if(password.length() < 8) throw new Exception("Password should be atleast of length 8");
    }

    public AuthResponseDto registerUser(UserCredDto registerUserDto, boolean isRegister) throws Exception {
        validateCredentials(registerUserDto, isRegister);
        User registeredUser = authService.registerUser(registerUserDto);
        return getAuthResponseDto(registeredUser);
    }

    public AuthResponseDto loginUser(UserCredDto loginUserDto, boolean isRegister) throws Exception {
        validateCredentials(loginUserDto, isRegister);
        User loginUser = authService.authenticate(loginUserDto);
        return getAuthResponseDto(loginUser);
    }

    private AuthResponseDto getAuthResponseDto(User loginUser) {
        String jwtAccessToken = jwtService.generateToken(loginUser);
        AuthResponseDto authDto = new AuthResponseDto();
        authDto.setAccessToken(jwtAccessToken);
        UserDetailsDto userDto = new UserDetailsDto();
        userDto.setId(loginUser.getId());
        userDto.setUsername(loginUser.getUsername());
        authDto.setUser(userDto);
        return authDto;
    }
}
