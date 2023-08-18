//package com.auctionapp.controller;
//
//import com.auctionapp.model.login.LoginRequest;
//import com.auctionapp.model.login.SignupRequest;
//import com.auctionapp.model.login.UserInfoResponse;
//import com.auctionapp.model.role.ERole;
//import com.auctionapp.model.role.Role;
//import com.auctionapp.model.user.User;
//import com.auctionapp.repository.UserRepository;
//import com.auctionapp.service.UserDetailsImpl;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.ResultActions;
//import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
//
//
//import java.util.*;
//
//import static org.mockito.Mockito.*;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
////TODO tetst return 403 needed clarify loginRequest, SignupRequest these are DTO's but we save USer and ect.
//@WebMvcTest(AuthController.class)
//class AuthControllerTest {
//
//    public static String URL = "/api/v1/auth";
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @MockBean
//    private UserRepository userRepository;
//
//    @MockBean
//    private AuthController authController;
//
//    private User user;
//
//    @BeforeEach
//    public void init() throws Exception {
//        Role roleUser = new Role();
//        roleUser.setId(1);
//        roleUser.setName(ERole.ROLE_USER);
//        Role roleAdmin = new Role();
//        roleAdmin.setId(2);
//        roleAdmin.setName(ERole.ROLE_ADMIN);
//
//        UserDetails userDetails = new UserDetailsImpl(
//                1L,
//                "username",
//                "username@username.com",
//                "password",
//                new ArrayList<GrantedAuthority>()
//        );
//
//        user = createUser(1L, "username", "123456", "test@test.com", Set.of(roleUser, roleAdmin));
//    }
//
//    @Test
//    @WithMockUser(username = "username")
//    void postRequest_RegisterUserMethod_Should() throws Exception {
//        SignupRequest signupRequest = new SignupRequest();
//        signupRequest.setUsername("username");
//        signupRequest.setEmail("newemail@example.com");
//        signupRequest.setPassword("newPassword");
//        signupRequest.setRole(new HashSet<>(Arrays.asList("user", "admin")));
//
//        UserInfoResponse userInfoResponse = new UserInfoResponse(1L, "username", "newemail@example.com", Collections.singletonList("ROLE_USER"));
//
//        when(userRepository.save(user)).thenReturn(user);
//
//        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
//                        .post(URL + "/signup")
//                        .characterEncoding("utf-8")
//                        .content(asJsonString(signupRequest))
//                        .contentType(MediaType.APPLICATION_JSON))
//
//                .andExpect(status().isCreated());
//
//        verify(userRepository, times(1)).save(user);
//    }
//
//    @Test
//    void postLoginUserTestNoJWT() throws Exception {
//        LoginRequest loginRequest = new LoginRequest();
//        loginRequest.setUsername("username");
//        loginRequest.setPassword("newPassword");
//        when(userRepository.save(user)).thenReturn(user);
//
//        ResultActions mvcResult = mockMvc.perform(MockMvcRequestBuilders
//                        .post(URL + "/signin")
//                        .characterEncoding("utf-8")
//                        .content(asJsonString(loginRequest))
//                        .contentType(MediaType.APPLICATION_JSON))
//                        .andExpect(status().isOk());
//        mvcResult.andExpect(header().exists(HttpHeaders.SET_COOKIE));
//    }
//
//
//    private User createUser(Long id, String username, String password, String email, Set<Role> roles) {
//        User user = new User();
//        user.setId(id);
//        user.setUsername(username);
//        user.setPassword(password);
//        user.setEmail(email);
//        user.setRoles(roles);
//        return user;
//    }
//
//    public static String asJsonString(final Object object) {
//        try {
//            return new ObjectMapper().writeValueAsString(object);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
//}