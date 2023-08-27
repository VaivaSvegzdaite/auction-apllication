package com.auctionapp.controller;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.bid.BidPriceDTO;
import com.auctionapp.service.BidService;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 36000)
@Slf4j
@RestController
@RequestMapping("/api/bid")
public class BidController {

    private static final Logger logger = LoggerFactory.getLogger(BidController.class);

    private final BidService bidService;
    private final ProductService productService;
    private final UserService userService;

    @Autowired
    public BidController(BidService bidService, ProductService productService, UserService userService) {
        this.bidService = bidService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BidDTO>> getAllBids() {
        logger.info("getAllBids method called.");
        List<BidDTO> listOfBidsResponse = bidService.findAll();
        logger.info("getAllBids method completed.");
        return ResponseEntity.ok(listOfBidsResponse);
    }

    @GetMapping("/product/{productId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<BidDTO>> getBidsByProduct(@PathVariable long productId) {
        logger.info("getBidsByProduct method called with productId: {}", productId);
        List<BidDTO> listOfBidsResponse = bidService.findByProductId(productId);
        logger.info("getBidsByProduct method completed.");
        return ResponseEntity.ok(listOfBidsResponse);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<BidDTO> getBidById(@PathVariable long id) {
        logger.info("getBidById method called with bidId: {}", id);
        BidDTO bid = bidService.findById(id);
        logger.info("getBidById method completed.");
        if (bid == null) {
            logger.info("No bid found for bidId: {}", id);
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bid);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createBid(@RequestBody BidDTO bidDTO) {
         Long userId = bidDTO.getUserId();
         Long productId = bidDTO.getProductId();
         if (userService.getUserById(userId).isEmpty()) {
             logger.warn("User for bidding doesn't exist.");
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for bidding doesn't exist");
         }
         if (productService.getProductById(productId).isEmpty()) {
             logger.warn("Product for bidding doesn't exist.");
             return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product for bidding doesn't exist");
         }
        logger.info("createBid method called.");
        Bid newBid = bidService.create(bidDTO);
        logger.info("Bid created successfully with id: {}", newBid.getId());
        return ResponseEntity.ok(newBid);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateBid(@PathVariable long id, @RequestBody BidPriceDTO bidPrice) {
        logger.info("updateBid method called for bidId: {}", id);
        BidDTO bid = bidService.findById(id);
        if (bid == null) {
            logger.info("No bid found for bidId: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid doesn't exist");
        }
        bidService.update(id, bidPrice);
        logger.info("Bid price updated successfully for bidId: {}", id);
        return ResponseEntity.ok("Bid price updated successfully");
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteBid(@PathVariable long id) {
        logger.info("deleteBid method called for bidId: {}", id);
        BidDTO bid = bidService.findById(id);
        if (bid == null) {
            logger.info("No bid found for bidId: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid not found");
        }
        bidService.deleteById(id);
        logger.info("Bid deleted successfully with bidId: {}", id);
        return ResponseEntity.ok("Bid was deleted successfully!");
    }
}
