package com.auctionapp.controller;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.service.AuctionService;
import com.auctionapp.service.BidService;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<List<AuctionDTO>> getAllAuctions() {
        List<AuctionDTO> auctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<AuctionDTO> getAuctionByProductId(@PathVariable Long productId) {
        Optional<Product> result = productService.getProductById(productId);
        AuctionDTO auction = auctionService.getAuctionByProductId(productId);
        if (auction == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(auction);
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
        if (productService.getProductById(auction.getProduct().getId()).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product for adding auction doesn't exist");
        }
        auctionService.createAuction(auction);
        return ResponseEntity.ok("Auction created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateAuction(@PathVariable Long id, @RequestBody AuctionDTO auctionDTO) {
        AuctionDTO auction = auctionService.getAuctionById(id);
        if (auction == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction doesn't exist");
        }
        auctionService.updateAuction(id, auctionDTO);
        return ResponseEntity.ok("Auction updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuction(@PathVariable Long id) {
        if (id != null && auctionService.getAuctionById(id) != null) {
            auctionService.deleteAuction(id);
            return ResponseEntity.ok("Auction was deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Auction for deletion doesn't exist!");
        }
    }


}
