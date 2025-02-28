package cn.edu.fudan.se.springboot101demo.controller;

import cn.edu.fudan.se.springboot101demo.DTO.AddBalanceRequest;
import cn.edu.fudan.se.springboot101demo.DTO.ChangeUsernameRequest;
import cn.edu.fudan.se.springboot101demo.DTO.NewUserRequest;
import cn.edu.fudan.se.springboot101demo.DTO.UserResponse;
import cn.edu.fudan.se.springboot101demo.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{userId}")
    public UserResponse getUserById(@PathVariable Long userId) {
        return userService.getUserResponseById(userId);
    }

    @PutMapping("/{userId}/username")
    public UserResponse changeUsername(@PathVariable Long userId, @RequestBody ChangeUsernameRequest request) {
        return userService.changeUsername(userId, request.name());
    }

    @PostMapping("/{userId}/balance")
    public UserResponse addBalance(@PathVariable Long userId, @RequestBody AddBalanceRequest request) {
        return userService.addBalance(userId, request.amount());
    }

}
