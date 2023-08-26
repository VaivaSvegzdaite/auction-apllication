package com.auctionapp.service;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.login.UserDTO;
import com.auctionapp.model.role.ERole;
import com.auctionapp.model.role.Role;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class AdminServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private AdminService adminService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    public void test_GetUsersMethod_ShouldReturnUsers() {
        Role adminRole = new Role();
        adminRole.setName(ERole.ROLE_ADMIN);
        Role userRole = new Role();
        userRole.setName(ERole.ROLE_USER);
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid());

        User adminUser = new User(1L, "admin", "admin@example.com", "adminPassword", Collections.singleton(adminRole), bids);
        User user1 = new User(2L, "user1", "user1@example.com", "user1Password", Collections.singleton(userRole), bids);
        User user2 = new User(3L, "user2", "user2@example.com", "user2Password", Collections.singleton(userRole), bids);
        List<User> userList = Arrays.asList(adminUser, user1, user2);

        when(userRepository.findAll()).thenReturn(userList);

        Collection<UserDTO> userDTOs = adminService.getUsers();

        assertEquals(2, userDTOs.size());
    }

    @Test
    public void test_DeleteUserMethod_ShouldRemoveUserAndReturnStatusOk() {
        Long id = 2L;
        Role userRole = new Role();
        userRole.setName(ERole.ROLE_USER);
        List<Bid> bids = new ArrayList<>();
        bids.add(new Bid());

        User userToDelete = new User(id, "userToDelete", "delete@example.com", "deletePassword", Collections.singleton(userRole), bids);
        when(userRepository.findById(id)).thenReturn(Optional.of(userToDelete));

        ResponseEntity<?> responseEntity = adminService.deleteUser(id);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

}