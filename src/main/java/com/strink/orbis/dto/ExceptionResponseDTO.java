package com.strink.orbis.dto;

import java.time.LocalDateTime;

public record ExceptionResponseDTO(
                LocalDateTime timestamp,
                int status,
                String error,
                String message,
                String path) {
}