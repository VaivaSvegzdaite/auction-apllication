package com.auctionapp.bid.repository;

import com.auctionapp.bid.model.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
//    List<Bid> findByProduct(Product product);
}
