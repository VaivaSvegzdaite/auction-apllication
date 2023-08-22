package com.auctionapp.controller;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.login.UserDTO;
import com.auctionapp.service.AdminService;
import com.auctionapp.service.AuctionService;
import com.auctionapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final AdminService adminService;

    private final UserService userService;

    private final AuctionService auctionService;

    public AdminController(AdminService adminService, UserService userService, AuctionService auctionService) {
        this.adminService = adminService;
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<UserDTO> getUsers() {
        return adminService.getUsers();
    }


    @DeleteMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteUser(@PathVariable final Long id) {
        var oUser = userService.getUserById(id);
        if (oUser.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for deletion not found");
        }
        return adminService.deleteUser(id);
    }

    @GetMapping("/auctions")
    @PreAuthorize("hasRole('ADMIN')")
    public Collection<Auction> getAuctions() {
        return auctionService.getAllAuctions();
    }


    @DeleteMapping("/auctions/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteAuction(@PathVariable final Long id) {
        var oAuction = auctionService.getAuctionById(id);
        if (oAuction.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction for deletion not found");
        }
        return adminService.deleteUser(id);
    }

}
