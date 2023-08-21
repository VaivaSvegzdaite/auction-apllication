package com.auctionapp.model.login;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestDTOTest {

    private SignupRequestDTO request;

    @BeforeEach
    public void setUp() {
        request = new SignupRequestDTO();
        request.setUsername("username");
        request.setEmail("user@example.com");
        request.setRole(Set.of("ROLE_USER"));
        request.setPassword("securePassword");
    }

    @Test
    void givenValidSignupRequest_whenValidating_ShouldNoExceptionThrow() {
        assertDoesNotThrow(() -> {
            validateSignupRequest(request);
        });
    }

    @Test
    void givenUsernameTooShort_whenValidating_ShouldThrowIllegalArgumentException() {
        request.setUsername("ab");
        assertThrows(IllegalArgumentException.class, () -> {
            validateSignupRequest(request);
        });
    }

    @Test
    void givenEmailInvalidFormat_whenValidating_ShouldThrowIllegalArgumentException() {
        request.setEmail("invalid_email");
        assertThrows(IllegalArgumentException.class, () -> {
            validateSignupRequest(request);
        });
    }

    @Test
    void givenPasswordTooShort_whenValidating_ShouldThrowIllegalArgumentException() {
        request.setPassword("pass");
        assertThrows(IllegalArgumentException.class, () -> {
            validateSignupRequest(request);
        });
    }

    private void validateSignupRequest(SignupRequestDTO request) {
        if (request.getUsername().isBlank() ||
                request.getEmail().isBlank() ||
                request.getPassword().isBlank() ||
                request.getUsername().length() < 3 || request.getUsername().length() > 20 ||
                request.getEmail().length() > 50 ||
                !request.getEmail().matches("\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b") ||
                request.getPassword().length() < 6 || request.getPassword().length() > 40) {
            throw new IllegalArgumentException("Invalid signup request");
        }
    }
}
