package cn.edu.fudan.se.springboot101demo.DTO;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record NewUserRequest(
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 20, message = "Username must be between 3 and 20 characters")
    String name,

    @NotNull(message = "Password cannot be null")
    @Size(min = 6, message = "Password must be at least 6 characters")
    String password
) {
}
