package com.auctionapp.controller;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.service.AuctionService;
import com.auctionapp.service.BidService;
import com.auctionapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {
    public final AuctionService auctionService;
    public final UserService userService;
    public final BidService bidService;

    public AuctionController(AuctionService auctionService, UserService userService, BidService bidService) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.bidService = bidService;
    }

    @GetMapping("/")
    public ResponseEntity<List<AuctionDTO>> getAllAuctions() {
        List<AuctionDTO> auctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AuctionDTO> getAuctionById(@PathVariable Long id) {
        AuctionDTO auction = auctionService.getAuctionById(id);
        if (auction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auction);
    }

    @PostMapping("/")
    public ResponseEntity<String> createAuction(@RequestBody Auction auction) {
        if (auction.getId() != null && auction.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }
        Long userId = auction.getUser().getId();
        if (userService.getUserById(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for adding auction doesn't exist");
        }
        auctionService.createAuction(auction);
        return ResponseEntity.ok("Auction created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuction(@PathVariable Long id, @RequestBody AuctionDTO auctionDTO) {
        if (id == null || auctionDTO.getUserId() == null) {
            return ResponseEntity.badRequest().body("Invalid request data!");
        }
        var oExistingAuction = auctionService.getAuctionById(id);
        if (oExistingAuction.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var existingAuction = oExistingAuction.get();
        existingAuction.setType(auctionDTO.getType());
        existingAuction.setStartTime(auctionDTO.getStartTime());
        existingAuction.setEndTime(auctionDTO.getEndTime());
        Long userId = auctionDTO.getUserId();
        if (userService.getUserById(userId).isPresent()) {
            existingAuction.getUser().setId(auctionDTO.getUserId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for updating auction doesn't exist!");
        }
        //do we update bids here? because there might be many bids associated with this auction
        auctionService.updateAuction(existingAuction);
        return ResponseEntity.ok("Auction was updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuction(@PathVariable Long id) {
        if (id != null && auctionService.getAuctionById(id).isPresent()) {
            auctionService.deleteAuction(id);
            return ResponseEntity.ok("Auction was deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction for deletion doesn't exist!");
        }
    }


}
