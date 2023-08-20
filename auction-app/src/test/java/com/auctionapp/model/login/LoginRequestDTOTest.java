package com.auctionapp.model.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDTOTest {

    private LoginRequestDTO request;

    @BeforeEach
    public void setUp() {
        request = new LoginRequestDTO();
    }

    @Test
    @DisplayName("Test login request with blank username")
    void givenBlankUsername_LoginRequest_ShouldThrowIllegalArgumentException() {
        request.setUsername("");
        request.setPassword("password");
        assertThrows(IllegalArgumentException.class, () -> {
            validateLoginRequest(request);
        });
    }

    @Test
    @DisplayName("Test login request with blank password")
    void givenBlankPassword_LoginRequest_ShouldThrowIllegalArgumentException() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("username");
        request.setPassword("");
        assertThrows(IllegalArgumentException.class, () -> {
            validateLoginRequest(request);
        });
    }

    @Test
    @DisplayName("Test valid login request")
    void givenValidUsernameAndPassword_LoginRequest_ShouldNoExceptionThrow() {
        LoginRequestDTO request = new LoginRequestDTO();
        request.setUsername("username");
        request.setPassword("password");
        assertDoesNotThrow(() -> {
            validateLoginRequest(request);
        });
    }

    private void validateLoginRequest(LoginRequestDTO request) {
        if (request.getUsername().isBlank() || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Username and password must not be blank");
        }
    }

}