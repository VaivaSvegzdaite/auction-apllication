package com.auctionapp.controller;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.service.AuctionService;
import com.auctionapp.service.BidService;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/api/auction")
public class AuctionController {

    private static final Logger logger = LoggerFactory.getLogger(AuctionController.class);

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
        logger.info("getAllAuctions method called.");
        List<Auction> auctions = auctionService.getAllAuctions();
        logger.info("getAllAuctions method completed.");
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Auction>> getAuctionsByUserId(@PathVariable Long userId) {
        logger.info("getAuctionsByUserId method called for user id: {}", userId);
        List<Auction> auctions = auctionService.getAuctionsByUserId(userId);
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Auction> getAuctionByProductId(@PathVariable Long productId) {
        logger.info("getAuctionByProductId method called with productId: {}", productId);
        Auction auction = auctionService.getAuctionByProductId(productId);
        logger.info("getAuctionByProductId method completed.");
        if (auction == null) {
            logger.info("No auction found for productId: {}", productId);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auction);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        logger.info("getAuctionById method called with auctionId: {}", id);
        Auction auction = auctionService.getAuctionById(id);
        logger.info("getAuctionById method completed.");
        if (auction == null) {
            logger.info("No auction found for auctionId: {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auction);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createAuction(@RequestBody Auction auction) {
        logger.info("createAuction method called.");
        if (auction.getId() != null && auction.getId() != 0) {
            logger.warn("Invalid request data: auctionId is not null or zero.");
            return ResponseEntity.badRequest().build();
        }
        Long userId = auction.getUser().getId();
        if (userService.getUserById(userId).isEmpty()) {
            logger.info("User for adding auction doesn't exist. UserId: {}", userId);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for adding auction doesn't exist");
        }
        if (productService.getProductById(auction.getProduct().getId()).isEmpty()) {
            logger.info("Product for adding auction doesn't exist. ProductId: {}", auction.getProduct().getId());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product for adding auction doesn't exist");
        }
        if (auctionService.getAuctionByProductId(auction.getProduct().getId()) != null) {
            logger.warn("This product already has an auction. ProductId: {}", auction.getProduct().getId());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("This product already has an auction.");
        }
        Auction newAuction = auctionService.createAuction(auction);
        logger.info("Auction created successfully with id: {}", newAuction.getId());
        return ResponseEntity.ok(newAuction);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateAuction(@PathVariable Long id, @RequestBody AuctionDTO auctionDTO) {
        logger.info("updateAuction method called for auctionId: {}", id);
        if (id == null) {
            logger.warn("Invalid request data: auctionId is null.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Invalid request data");
        }
        Auction updatedAuction = auctionService.updateAuction(id, auctionDTO);
        if (updatedAuction == null) {
            logger.warn("No auction found for auctionId: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction doesn't exist");
        }
        logger.info("Auction updated successfully with id: {}", id);
        return ResponseEntity.ok(updatedAuction);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteAuction(@PathVariable Long id) {
        logger.info("deleteAuction method called for auctionId: {}", id);
        if (id != null && auctionService.getAuctionById(id) != null) {
            auctionService.deleteAuction(id);
            logger.info("Auction deleted successfully with id: {}", id);
            return ResponseEntity.ok("Auction was deleted successfully!");
        } else {
            logger.warn("Auction for deletion doesn't exist. AuctionId: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction for deletion doesn't exist");
        }
    }
}
