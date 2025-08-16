package com.strink.orbis.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthResponseDto {

    String accessToken;

    UserDetailsDto user;

}
