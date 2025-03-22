package cn.edu.fudan.se.springboot101demo.service;

import cn.edu.fudan.se.springboot101demo.DTO.UserResponse;
import cn.edu.fudan.se.springboot101demo.entity.User;
import cn.edu.fudan.se.springboot101demo.exception.InvalidRequestException;
import cn.edu.fudan.se.springboot101demo.exception.NotFoundException;
import cn.edu.fudan.se.springboot101demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    void testChangeUsername_Success() {
        // Arrange（准备测试数据）
        Long userId = 1L;
        String oldName = "Alice";
        String newName = "Bob";

        User mockUser = new User(userId, oldName, "encryptedPwd", 100);

        // Arrange（模拟依赖行为）
        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.existsByName(newName)).thenReturn(false);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0)); // 返回保存的用户对象

        // Act（执行测试方法）
        UserResponse response = userService.changeUsername(userId, newName);

        // Assert（断言结果）
        assertThat(response.id()).isEqualTo(userId);
        assertThat(response.name()).isEqualTo(newName);
        assertThat(response.balance()).isEqualTo(mockUser.getBalance());
    }

    @Test
    void testChangeUsername_UserNotFound() {
        // Arrange
        Long userId = 999L;
        String newName = "Bob";

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> userService.changeUsername(userId, newName))
            .isInstanceOf(NotFoundException.class)
            .hasMessage("User not found");
    }


    @Test
    void testChangeUsername_NameAlreadyExists() {
        // Arrange
        Long userId = 1L;
        String oldName = "Alice";
        String newName = "Bob";

        User mockUser = new User(userId, oldName, "encryptedPwd", 100);

        when(userRepository.findById(userId)).thenReturn(Optional.of(mockUser));
        when(userRepository.existsByName(newName)).thenReturn(true); // 模拟用户名已存在

        // Act & Assert
        assertThatThrownBy(() -> userService.changeUsername(userId, newName))
            .isInstanceOf(InvalidRequestException.class)
            .hasMessage("Username already exists");
    }

}