package com.auctionapp.repository;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByProduct(Product product);
}
