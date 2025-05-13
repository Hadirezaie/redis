package com.newcoders.redis.redis.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.newcoders.redis.redis.domain.User;
import com.newcoders.redis.redis.repository.UserRepository;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize the mocks
        userService = new UserService(userRepository); // Instantiate UserService with mocked UserRepository
    }

    @Test
    public void testGetUserById() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");
        user.setEmail("testuser@example.com");
        user.setPassword("password123");

        when(userRepository.findById(userId)).thenReturn(java.util.Optional.of(user));

        User result = userService.getUserById(userId);

        assertEquals(userId, result.getId());
        assertEquals("testuser", result.getUsername());
        verify(userRepository, times(1)).findById(userId); // Verifying that findById was called exactly once
    }

    @Test
    public void testSaveUser() {
        User user = new User();
        user.setUsername("newuser");
        user.setEmail("newuser@example.com");
        user.setPassword("password123");

        when(userRepository.save(user)).thenReturn(user);

        User savedUser = userService.saveUser(user);

        assertEquals("newuser", savedUser.getUsername());
        verify(userRepository, times(1)).save(user); // Verifying that save was called exactly once
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        doNothing().when(userRepository).deleteById(userId);

        userService.deleteUser(userId);

        verify(userRepository, times(1)).deleteById(userId); // Verifying that deleteById was called exactly once
    }

    @Test
    public void testUpdateUser() {
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("updateduser");
        user.setEmail("updateduser@example.com");
        user.setPassword("newpassword");

        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(user);

        assertEquals("updateduser", updatedUser.getUsername());
        verify(userRepository, times(1)).save(user); // Verifying that save was called exactly once
    }

}
