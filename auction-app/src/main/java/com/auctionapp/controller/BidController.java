package com.auctionapp.controller;


import com.auctionapp.model.bid.Bid;
import com.auctionapp.service.BidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class BidController {

    private BidService bidService;

    @Autowired
    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    @GetMapping("/bids")
    public List<Bid> getAllBids() {
        return bidService.findAll();
    }

//    @GetMapping("/bids/product/{id}")
//    public List<Bid> getBidsByProduct(@PathVariable long productId) {
//        return bidService.findByProductId(productId);
//    }

    @GetMapping("/bids/{id}")
    public Bid getBidById(@PathVariable long id) {
        Bid bid = bidService.findById(id);
        if (bid == null) {
            throw new RuntimeException("There are no bid with id " + id);
        }
        return bid;
    }

    @PostMapping("/bids")
    public Bid createBid(@RequestBody Bid bid) {
        return bidService.save(bid);
    }

    @PutMapping("/bids")
    public Bid updateBid(@RequestBody Bid bid) {
        return bidService.save(bid);
    }

    @DeleteMapping("/bids/{id}")
    public void deleteBid(@PathVariable long id) {
        Bid bid = bidService.findById(id);
        if (bid == null) {
            throw new RuntimeException("There are no bid with id " + id);
        }
        bidService.deleteById(id);
    }
}
