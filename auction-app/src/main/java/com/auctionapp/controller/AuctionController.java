package com.auctionapp.controller;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.service.AuctionService;
import com.auctionapp.service.BidService;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auction")
public class AuctionController {
    public final AuctionService auctionService;
    public final UserService userService;
    public final BidService bidService;
    public final ProductService productService;

    public AuctionController(AuctionService auctionService, UserService userService, BidService bidService,
                             ProductService productService) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.bidService = bidService;
        this.productService = productService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<AuctionDTO>> getAuctionsByProductId(@PathVariable Long productId) {
        List<AuctionDTO> auctions = auctionService.getAuctionsByProductId(productId);
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<AuctionDTO> getAuctionById(@PathVariable Long id) {
        AuctionDTO auction = auctionService.getAuctionById(id);
        if (auction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auction);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createAuction(@RequestBody Auction auction) {
        if (auction.getId() != null && auction.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }
        Long userId = auction.getUser().getId();
        if (userService.getUserById(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for adding auction doesn't exist");
        }
        if (productService.getProductById(auction.getProduct().getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product for adding auction doesn't exist");
        }
        Auction newAuction = auctionService.createAuction(auction);
        AuctionDTO createdAuctionDTO = auctionService.getAuctionById(auction.getId());
        return ResponseEntity.ok(createdAuctionDTO);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateAuction(@PathVariable Long id, @RequestBody AuctionDTO auctionDTO) {
        AuctionDTO auction = auctionService.getAuctionById(id);
        if (auction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction doesn't exist");
        }
        auctionService.updateAuction(id, auctionDTO);
        AuctionDTO updatedAuctionDTO = auctionService.getAuctionById(id);
        return ResponseEntity.ok(updatedAuctionDTO);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteAuction(@PathVariable Long id) {
        if (id != null && auctionService.getAuctionById(id) != null) {
            auctionService.deleteAuction(id);
            return ResponseEntity.ok("Auction was deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction for deletion doesn't exist!");
        }
    }


}
