package com.auctionapp.service;

import com.auctionapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.auctionapp.model.user.User;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@AutoConfigureMockMvc
class UserDetailsServiceImplTest {

    @Spy
    private UserRepository userRepository;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    private User user;
    private UserDetails userDetails;

    @BeforeEach
    private void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test existing user return when loadUserByUsername")
    public void checkUsername_LoadByUsernameMethod_ShouldReturnUsername() {
        user = new User();
        user.setId(1L);
        user.setUsername("Test User");
        userRepository.save(user);

        when(userRepository.findByUsername("Test User")).thenReturn(Optional.of(user));

        userDetails = userDetailsService.loadUserByUsername("Test User");

        assertNotNull(userDetails);
        assertEquals("Test User", userDetails.getUsername());
    }

    @Test
    @DisplayName("Test non existing user when loadUserByUsername throws UsernameNotFoundException")
    public void checkUsername_LoadByUsernameMethod_ShouldThrowUsernameNotFoundException() {
        when(userRepository.findByUsername("non_existing_user")).thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> userDetailsService.loadUserByUsername("non_existing_user")
        );
    }

}