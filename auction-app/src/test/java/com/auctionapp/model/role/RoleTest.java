package com.auctionapp.model.role;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoleTest {

    private Role role;

    private Role role1;


    @BeforeEach
    public void setUp() {
        role = new Role();
        role1 = new Role();
    }

    @Test
    @DisplayName("Test role creation")
    public void testCreateRole_NoArgsConstructor_ShouldReturnRoleNotNull() {
        assertNotNull(role);
    }


    @Test
    @DisplayName("Test role getters and setters")
    public void checkRoleName_SettingAndGetting_ShouldReturnRoleName() {
        role.setName(ERole.ROLE_ADMIN);
        assertEquals(ERole.ROLE_ADMIN, role.getName());
    }


    @Test
    @DisplayName("Test role getters and setters return not equal roles")
    public void givenTwoRolesWithDifferentIdAndName_SettingAndGetting_ShouldReturnNotEqualRoles() {
        role.setId(1);
        role.setName(ERole.ROLE_USER);

        role1.setId(2);
        role.setName(ERole.ROLE_ADMIN);

        assertNotEquals(role, role1);
    }

}



