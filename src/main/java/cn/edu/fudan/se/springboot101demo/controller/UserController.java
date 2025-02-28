package cn.edu.fudan.se.springboot101demo.controller;

import cn.edu.fudan.se.springboot101demo.DTO.NewUserRequest;
import cn.edu.fudan.se.springboot101demo.DTO.UserResponse;
import cn.edu.fudan.se.springboot101demo.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public UserResponse registerUser(@RequestBody NewUserRequest request) {
        return userService.registerUser(request);
    }
}
