package com.auctionapp.service;

import com.auctionapp.model.login.UserDTO;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public Optional<User> getUserById(Long id) {
        logger.info("Getting user by ID: {}", id);
        return userRepository.findById(id);
    }

}
