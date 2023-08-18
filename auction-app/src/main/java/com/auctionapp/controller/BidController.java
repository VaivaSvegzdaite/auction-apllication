package com.auctionapp.controller;


import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.bid.BidPriceDTO;
import com.auctionapp.service.BidService;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bid")
public class BidController {

    private BidService bidService;
    private ProductService productService;
    private UserService userService;

    @Autowired
    public BidController(BidService bidService, ProductService productService, UserService userService) {
        this.bidService = bidService;
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<BidDTO>> getAllBids() {
        List<BidDTO> listOfBidsResponse =  bidService.findAll();
        return ResponseEntity.ok(listOfBidsResponse);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<BidDTO>> getBidsByProduct(@PathVariable long productId) {
        List<BidDTO> listOfBidsResponse =  bidService.findByProductId(productId);
        return ResponseEntity.ok(listOfBidsResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BidDTO> getBidById(@PathVariable long id) {
        BidDTO bid = bidService.findById(id);
        if (bid == null) {
           return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(bid);
    }

    @PostMapping("/")
    public ResponseEntity<String> createBid(@RequestBody Bid bid) {
        Long userId = bid.getUser().getId();
        Long productId = bid.getProduct().getId();
        if (userService.getUserById(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for bidding doesn't exist");
        }
        if (productService.getProductById(productId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product for bidding doesn't exist");
        }
        BidDTO bidDTO = bidService.create(bid);
        return ResponseEntity.ok("Bid created successfully with id " + bidDTO.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateBid(@PathVariable long id, @RequestBody BidPriceDTO bidPrice) {
        BidDTO bid = bidService.findById(id);
        if (bid == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid doesn't exist");
        }
        bidService.update(id, bidPrice);
        return ResponseEntity.ok("Bid price updated successfully");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBid(@PathVariable long id) {
        BidDTO bid = bidService.findById(id);
        if (bid == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Bid not found");
        }
        bidService.deleteById(id);
        return ResponseEntity.ok("Bid was deleted successfully!");
    }
}
