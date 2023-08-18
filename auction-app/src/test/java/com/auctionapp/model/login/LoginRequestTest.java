package com.auctionapp.model.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestTest {

    private LoginRequest request;

    @BeforeEach
    public void setUp() {
        request = new LoginRequest();
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
        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("");
        assertThrows(IllegalArgumentException.class, () -> {
            validateLoginRequest(request);
        });
    }

    @Test
    @DisplayName("Test valid login request")
    void givenValidUsernameAndPassword_LoginRequest_ShouldNoExceptionThrow() {
        LoginRequest request = new LoginRequest();
        request.setUsername("username");
        request.setPassword("password");
        assertDoesNotThrow(() -> {
            validateLoginRequest(request);
        });
    }

    private void validateLoginRequest(LoginRequest request) {
        if (request.getUsername().isBlank() || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Username and password must not be blank");
        }
    }

}