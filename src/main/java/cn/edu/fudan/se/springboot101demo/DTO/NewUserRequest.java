package cn.edu.fudan.se.springboot101demo.DTO;

public record NewUserRequest(
    String name,
    String password
) {
}
