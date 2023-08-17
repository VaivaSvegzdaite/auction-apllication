package com.auctionapp.repository;

import com.auctionapp.model.bid.Bid;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BidRepository extends JpaRepository<Bid, Long> {
//    List<Bid> findByProduct(Product product);
}
