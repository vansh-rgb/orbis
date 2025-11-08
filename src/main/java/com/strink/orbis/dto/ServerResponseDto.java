package com.strink.orbis.dto;

import org.springframework.http.HttpStatus;

public record ServerResponseDto(HttpStatus status, String error, Object data) {}
