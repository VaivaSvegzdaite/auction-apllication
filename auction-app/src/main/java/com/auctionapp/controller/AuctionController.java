package com.auctionapp.controller;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.product.Product;
import com.auctionapp.service.AuctionService;
import com.auctionapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/auction")
public class AuctionController {
    public final AuctionService auctionService;

    public AuctionController(AuctionService auctionService) {
        this.auctionService = auctionService;
    }

    @GetMapping()
    public ResponseEntity<List<Auction>> getAllAuctions() {
        List<Auction> auctions = auctionService.getAllAuctions();
        return ResponseEntity.ok(auctions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Auction> getAuctionById(@PathVariable Long id) {
        Optional<Auction> optionalAuction = auctionService.getAuctionById(id);
        return optionalAuction.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    public ResponseEntity<Auction> createAuction(@RequestBody Auction auction) {
        if (auction.getId() != null && auction.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }

        Auction createdAuction = auctionService.createAuction(auction);
        return ResponseEntity.ok(createdAuction);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Auction> updateAuction(@PathVariable Long id, @RequestBody Auction auction) {
        if (auction.getId() == null || auction.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Auction updatedAuction = auctionService.updateAuction(auction);
        return ResponseEntity.ok(updatedAuction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAuction(@PathVariable Long id) {
        auctionService.deleteAuction(id);
        return ResponseEntity.ok("Auction was deleted!");
    }
}
