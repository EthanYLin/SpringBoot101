package cn.edu.fudan.se.springboot101demo.controller;

import cn.edu.fudan.se.springboot101demo.DTO.ChangeUsernameRequest;
import cn.edu.fudan.se.springboot101demo.DTO.UserResponse;
import cn.edu.fudan.se.springboot101demo.exception.NotFoundException;
import cn.edu.fudan.se.springboot101demo.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testChangeUsername_Success() throws Exception {
        // Arrange
        Long userId = 1L;
        String newName = "NewUsername";

        ChangeUsernameRequest request = new ChangeUsernameRequest(newName);
        UserResponse expectedResponse = new UserResponse(userId, newName, 100);

        when(userService.changeUsername(userId, newName)).thenReturn(expectedResponse);

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/username", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isOk())
               .andExpect(content().json(objectMapper.writeValueAsString(expectedResponse)));
    }

    @Test
    void testChangeUsername_UserNotFound() throws Exception {
        // Arrange
        Long userId = 999L;
        String newName = "NewUsername";
        ChangeUsernameRequest request = new ChangeUsernameRequest(newName);

        // 模拟 Service 抛出 NotFoundException
        when(userService.changeUsername(userId, newName))
            .thenThrow(new NotFoundException("User not found"));

        // Act & Assert
        mockMvc.perform(put("/users/{userId}/username", userId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
               .andExpect(status().isNotFound());
    }

}