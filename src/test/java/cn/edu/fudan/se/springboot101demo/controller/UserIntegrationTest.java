package cn.edu.fudan.se.springboot101demo.controller;

import cn.edu.fudan.se.springboot101demo.DTO.ChangeUsernameRequest;
import cn.edu.fudan.se.springboot101demo.DTO.UserResponse;
import cn.edu.fudan.se.springboot101demo.entity.User;
import cn.edu.fudan.se.springboot101demo.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class UserIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private UserRepository userRepository;

    private Long userId1;
    private static final String userName1 = "Alice";
    private static final String userName2 = "Bob";
    private static final String newName = "Cathy";
    private static final String encryptedPassword = "encryptedPwd";
    private static final Integer balance = 100;


    @BeforeEach
    void setUp() {
        User user1 = new User(null, userName1, encryptedPassword, balance);
        userId1 = userRepository.save(user1).getId();
        User user2 = new User(null, userName2, encryptedPassword, balance);
        userRepository.save(user2);
    }

    @Test
    void testChangeUsername_Success() throws Exception {
        // Arrange
        ChangeUsernameRequest request = new ChangeUsernameRequest(newName);
        UserResponse expectedResponse = new UserResponse(userId1, newName, balance);

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/username", userId1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));

        // 验证数据库更新是否生效
        User user = userRepository.findById(userId1).orElseThrow();
        assertThat(user.getName()).isEqualTo(newName);
    }

    @Test
    void testChangeUsername_UserNotFound() throws Exception {
        // Arrange
        Long nonexistentId = 999L; // 数据库中没有的 ID
        ChangeUsernameRequest request = new ChangeUsernameRequest(newName);

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/username", nonexistentId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isNotFound());
    }

    @Test
    void testChangeUsername_NameAlreadyExists() throws Exception {
        // Arrange
        ChangeUsernameRequest request = new ChangeUsernameRequest(userName2);

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/username", userId1)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isBadRequest());

        // 验证数据库中的用户名没有被更改
        User user = userRepository.findById(userId1).orElseThrow();
        assertThat(user.getName()).isEqualTo(userName1);
    }

}

