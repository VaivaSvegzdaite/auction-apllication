package com.auctionapp.model.user;

import static org.junit.jupiter.api.Assertions.*;


import com.auctionapp.model.role.ERole;
import com.auctionapp.model.role.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class UserTest {

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
    }

    @Test
    @DisplayName("Test user creation")
    public void testCreateUser_NoArgsConstructor_ShouldReturnRoleNotNull() {
        assertNotNull(user);
    }


    @Test
    @DisplayName("Test getter and setter for username")
    public void checkUserUsername_SettingAndGetting_ShouldReturnUserUsername() {
        user.setUsername("john_doe");
        assertEquals("john_doe", user.getUsername());
    }

    @Test
    @DisplayName("Test getter and setter for email")
    public void checkUserEmail_SettingAndGetting_ShouldReturnUserEmail() {
        user.setEmail("john@example.com");
        assertEquals("john@example.com", user.getEmail());
    }

    @Test
    @DisplayName("Test getter and setter for password")
    public void checkUserPassword_SettingAndGetting_ShouldReturnUserPassword() {
        user.setPassword("password123");
        assertEquals("password123", user.getPassword());
    }

    @Test
    @DisplayName("Test getter and setter for roles")
    public void testRolesSettingAndGetting_ShouldReturnRoles() {
        Role role1 = new Role();
        role1.setName(ERole.ROLE_USER);

        Role role2 = new Role();
        role2.setName(ERole.ROLE_ADMIN);

        user.getRoles().add(role1);
        user.getRoles().add(role2);

        assertEquals(2, user.getRoles().size());
    }
}
