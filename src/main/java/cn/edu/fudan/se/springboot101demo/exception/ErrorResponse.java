package cn.edu.fudan.se.springboot101demo.exception;

import java.time.LocalDateTime;

public record ErrorResponse(
    LocalDateTime timestamp,
    int status,
    String error
) {
    public ErrorResponse(int status, String error) {
        this(LocalDateTime.now(), status, error);
    }
}
