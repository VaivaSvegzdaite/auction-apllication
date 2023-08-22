package com.auctionapp.service;

import com.auctionapp.model.login.MessageResponseDTO;
import com.auctionapp.model.login.SignupRequestDTO;
import com.auctionapp.model.login.UserDTO;
import com.auctionapp.model.role.ERole;
import com.auctionapp.model.role.Role;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.RoleRepository;
import com.auctionapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminService.class);
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    private final RoleRepository roleRepository;


    public AdminService(UserRepository userRepository, PasswordEncoder encoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
    }



    @Transactional(readOnly = true)
    public Collection<UserDTO> getUsers() {
        return userRepository.findAll().stream().filter(user -> !user.getUsername().equals("admin"))
                .map(userFromDb -> new UserDTO(userFromDb.getId(), userFromDb.getUsername(), userFromDb.getEmail(), userFromDb.getPassword(), userFromDb.getRoles()))
                .collect(Collectors.toList());
    }

    @Transactional
    public ResponseEntity<?> deleteUser(Long id) {
        userRepository.findById(id).ifPresent(user -> userRepository.deleteById(user.getId()));
        logger.info("Admin deleted user with id: {}", id);
        return ResponseEntity.ok(new MessageResponseDTO("User deleted successfully!"));
    }

}
