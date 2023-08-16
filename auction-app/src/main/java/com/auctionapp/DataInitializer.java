package com.auctionapp;

import com.auctionapp.login.model.ERole;
import com.auctionapp.login.model.Role;
import com.auctionapp.login.model.User;
import com.auctionapp.login.repository.RoleRepository;
import com.auctionapp.login.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Set;


@Component
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    public DataInitializer(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public void run(String... args) {
        initializeRoles();
        initializeAdminUser();
    }

    private void initializeRoles() {
        Optional<Role> userRole = roleRepository.findByName(ERole.ROLE_USER);
        Optional<Role> adminRole = roleRepository.findByName(ERole.ROLE_ADMIN);

        if (userRole.isEmpty()) {
            Role newUserRole = new Role(ERole.ROLE_USER);
            roleRepository.save(newUserRole);
        }

        if (adminRole.isEmpty()) {
            Role newAdminRole = new Role(ERole.ROLE_ADMIN);
            roleRepository.save(newAdminRole);
        }
    }

    private void initializeAdminUser() {
        if (userRepository.existsByUsername("admin")) {
            return;
        }

        var oUserRole = roleRepository.findByName(ERole.ROLE_USER);
        var oAdminRole = roleRepository.findByName(ERole.ROLE_ADMIN);

        if (oUserRole.isPresent() && oAdminRole.isPresent()) {
            User admin = new User("admin", "admin@admin.com", "$2a$12$j5nzRW4NXU0.d7GdIjYmxOWJLHQoynpYX3W07dhMcjG7t4kgh6D/O");
            admin.setRoles(Set.of(oUserRole.get(), oAdminRole.get()));
            userRepository.save(admin);
        }
    }

}
