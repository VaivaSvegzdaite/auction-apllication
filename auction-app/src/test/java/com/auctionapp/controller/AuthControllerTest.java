package com.auctionapp.controller;

import com.auctionapp.config.jwt.JwtUtils;
import com.auctionapp.model.role.ERole;
import com.auctionapp.model.role.Role;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.RoleRepository;
import com.auctionapp.repository.UserRepository;
import com.auctionapp.service.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.*;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.get;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
class AuthControllerTest {

    public static String URL = "/api/v1/auth";

//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private AuthController authController;
//    @MockBean
//    private UserRepository userRepository;
//    @MockBean
//    private AuthenticationManager authenticationManager;
//    @MockBean
//    JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter;
//    @MockBean
//    private RoleRepository roleRepository;
//    @MockBean
//    private PasswordEncoder passwordEncoder;
//    @MockBean
//    private UserDetailsService userDetailsService;
//    @MockBean
//    private JwtUtils jwtUtils;
@Autowired
private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private JwtUtils jwtUtils;

   
    private User user;

    private List<User> users;

    @BeforeEach
    public void init() throws Exception {
        Role roleUser = new Role();
        roleUser.setId(1);
        roleUser.setName(ERole.ROLE_USER);
        Role roleAdmin = new Role();
        roleAdmin.setId(2);
        roleAdmin.setName(ERole.ROLE_ADMIN);

        UserDetails userDetails = new UserDetailsImpl(
                1L,
                "username",
                "username@username.com",
                "password",
                new ArrayList<GrantedAuthority>()
        );

        user = createUser(1L, "username", "123456", "test@test.com", Set.of(roleUser, roleAdmin));
        users = createUsersList(user);
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        // Mock the authentication response
        Authentication authentication = new UsernamePasswordAuthenticationToken("username", "password");
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        
        // Create a mock instance of JwtUtils
        JwtUtils jwtUtilsMock = Mockito.mock(JwtUtils.class);


        ResponseCookie mockJwtCookie = ResponseCookie.from("jwt-cookie", "mocked-jwt-value")
                .httpOnly(true)
                .maxAge(Duration.ofHours(1))
                .build();
        when(jwtUtilsMock.generateJwtCookie(any(UserDetailsImpl.class))).thenReturn(mockJwtCookie);


        // Mock JWT generation
        when(jwtUtils.generateJwtCookie(any())).thenReturn(mockJwtCookie);

        // Perform the request and expect a successful response
        ResultActions perform = mockMvc.perform(post("/signin")
                .content(asJsonString(user))
                .contentType(MediaType.APPLICATION_JSON)
                .andExpect(status().isOk()));
//                .andExpect(header().exists(HttpHeaders.SET_COOKIE))
//                .andExpect(/* Perform more assertions on the response body if needed */);
    }

//    @Test
//    void givenUserAuthenticated_whenGetGreet_thenOk() throws Exception {
//        ResultActions mvcResult =  api.perform(post("/signup").with(SecurityMockMvcRequestPostProcessors.jwt()
//                        .authorities(new SimpleGrantedAuthority("NICE"), new SimpleGrantedAuthority("AUTHOR"))))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.body").value("Hi user! You are granted with: [NICE, AUTHOR]."));
//    }
//
//    @Test
//    void postUserTest() throws Exception {
//
//        when(userRepository.save(user)).thenReturn(user);
//
//        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
//                        .post(URL + "/signup")
//                        .with(SecurityMockMvcRequestPostProcessors.jwt())
//                        .content(asJsonString(user))
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isCreated());
//
//        verify(userRepository, times(1)).save(user);
//    }

    private User createUser(Long id, String username, String password, String email, Set<Role> roles) {
        User user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRoles(roles);
        return user;
    }

    private List<User> createUsersList(User user) {
        List<User> users = new ArrayList<>();
        users.add(user);
        users.add(user);
        return users;
    }

    public static String asJsonString(final Object object) {
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}