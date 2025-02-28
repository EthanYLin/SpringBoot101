package cn.edu.fudan.se.springboot101demo.DTO;

import cn.edu.fudan.se.springboot101demo.entity.User;

public record UserResponse(
    Long id,
    String name,
    Integer balance
) {
    public UserResponse(User user){
        this(user.getId(), user.getName(), user.getBalance());
    }
}
