package com.strink.orbis.dto;


public record AuthResponseDto(String accessToken, UserDetailsDto user) {}
