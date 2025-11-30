package com.strink.orbis.service;

import com.strink.orbis.dto.AuthResponseDto;
import com.strink.orbis.dto.UserCredDto;
import com.strink.orbis.dto.UserDetailsDto;
import com.strink.orbis.exception.DuplicateResourceException;
import com.strink.orbis.exception.ValidationException;
import com.strink.orbis.model.User;
import com.strink.orbis.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final AuthService authService;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public void validateCredentials(UserCredDto registerUserDto, boolean isRegister) {
        validateUsername(registerUserDto.username());
        validatePassword(registerUserDto.password());
        if (isRegister) {
            checkUserInDB(registerUserDto);
        }
    }

    private void checkUserInDB(UserCredDto registerUserDto) {
        Optional<User> user = userRepository.findByUsername(registerUserDto.username());
        if (user.isPresent()) {
            throw new DuplicateResourceException("User", registerUserDto.username());
        }
    }

    private void validateUsername(String username) {
        if (username.length() < 6)
            throw new ValidationException("username", "should be at least 6 characters long");
    }

    private void validatePassword(String password) {
        if (password.length() < 8)
            throw new ValidationException("password", "should be at least 8 characters long");
    }

    public AuthResponseDto registerUser(UserCredDto registerUserDto, boolean isRegister) {
        validateCredentials(registerUserDto, isRegister);
        User registeredUser = authService.registerUser(registerUserDto);
        return getAuthResponseDto(registeredUser);
    }

    public AuthResponseDto loginUser(UserCredDto loginUserDto, boolean isRegister) {
        validateCredentials(loginUserDto, isRegister);
        User loginUser = authService.authenticate(loginUserDto);
        return getAuthResponseDto(loginUser);
    }

    private AuthResponseDto getAuthResponseDto(User loginUser) {
        String jwtAccessToken = jwtService.generateToken(loginUser);
        UserDetailsDto userDto = new UserDetailsDto(loginUser.getId(), loginUser.getUsername());
        return new AuthResponseDto(jwtAccessToken, userDto);
    }
}
