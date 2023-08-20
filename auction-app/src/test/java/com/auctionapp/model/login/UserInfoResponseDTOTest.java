package com.auctionapp.model.login;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserInfoResponseDTOTest {

    @Test
    void givenUserInfo_whenCreatingUserInfoResponse_ShouldFieldsBeSet() {
        Long id = 1L;
        String username = "john_doe";
        String email = "john@example.com";
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        String token = "ABC";

        UserInfoResponseDTO response = new UserInfoResponseDTO(id, username, email, roles, token);

        assertEquals(id, response.getId());
        assertEquals(username, response.getUsername());
        assertEquals(email, response.getEmail());
        assertEquals(roles, response.getRoles());
        assertEquals(token, response.getToken());
    }

}