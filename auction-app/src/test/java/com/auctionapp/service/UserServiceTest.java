package com.auctionapp.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.auctionapp.model.role.ERole;
import com.auctionapp.model.role.Role;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;
import java.util.Set;

@SpringBootTest
public class UserServiceTest {

    private User user;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        Long userId = 1L;
        Role role1 = new Role();
        role1.setName(ERole.ROLE_USER);

        Role role2 = new Role();
        role2.setName(ERole.ROLE_ADMIN);
        user = new User(userId, "john_doe", "john@example.com", "123456", Set.of(role1, role2));
    }

    @Test
    @DisplayName("Test existing user to be returned")
    void givenUserId_GetUserByIdMethod_ShouldReturnUserWithExistingUserId() {
        Long userId = 1L;
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        UserService userService = new UserService(userRepository);
        Optional<User> result = userService.getUserById(userId);
        assertTrue(result.isPresent());
        assertEquals(user, result.get());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    @DisplayName("Test non existing user to return empty Optional")
    void givenNonExistentUserId_GetUserByIdMethod_ShouldReturnEmptyOptional() {
        Long userId = 2L;
        when(userRepository.findById(userId)).thenReturn(Optional.empty());
        UserService userService = new UserService(userRepository);
        Optional<User> result = userService.getUserById(userId);
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }
}
