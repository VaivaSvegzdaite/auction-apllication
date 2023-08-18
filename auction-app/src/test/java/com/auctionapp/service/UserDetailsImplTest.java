package com.auctionapp.service;

import com.auctionapp.model.role.ERole;
import com.auctionapp.model.role.Role;
import com.auctionapp.model.user.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class UserDetailsImplTest {

    @Test
    @DisplayName("Test user details impl build")
    public void testUserDetailsImplBuild() {
        User user = new User();
        user.setId(1L);
        user.setUsername("john_doe");
        user.setEmail("john@example.com");
        user.setPassword("password123");

        Role role = new Role();
        role.setName(ERole.ROLE_USER);
        user.setRoles(Collections.singleton(role));

        UserDetailsImpl userDetails = UserDetailsImpl.build(user);

        assertEquals(user.getId(), userDetails.getId());
        assertEquals(user.getUsername(), userDetails.getUsername());
        assertEquals(user.getEmail(), userDetails.getEmail());
        assertEquals(user.getPassword(), userDetails.getPassword());

        assertEquals(1, userDetails.getAuthorities().size());
        assertTrue(userDetails.getAuthorities().contains(new SimpleGrantedAuthority(ERole.ROLE_USER.name())));
    }

}