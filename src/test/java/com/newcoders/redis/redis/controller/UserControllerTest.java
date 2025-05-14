package com.newcoders.redis.redis.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.newcoders.redis.redis.config.MockRedisConfig;
import com.newcoders.redis.redis.domain.User;
import com.newcoders.redis.redis.service.UserService;

// integration test
@WebMvcTest(UserController.class)
@Import(MockRedisConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");
    }

    @Test
    public void testGetUserById() throws Exception {
        when(userService.getUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/users/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"))
                .andExpect(jsonPath("$.email").value("testuser@example.com"));
    }

    @Test
    public void testCreateUser() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users")
                .contentType("application/json")
                .content(
                        "{ \"username\": \"testuser\", \"email\": \"testuser@example.com\", \"password\": \"password123\" }"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testUpdateUser() throws Exception {
        when(userService.updateUser(any(User.class))).thenReturn(user);
        mockMvc.perform(put("/api/users/1")
                .contentType("application/json")
                .content(
                        "{ \"username\": \"updateduser\", \"email\": \"updateduser@example.com\", \"password\": \"newpassword\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));
    }

    @Test
    public void testDeleteUser() throws Exception {
        doNothing().when(userService).deleteUser(1L);
        mockMvc.perform(delete("/api/users/1"))
                .andExpect(status().isNoContent());
    }

}
