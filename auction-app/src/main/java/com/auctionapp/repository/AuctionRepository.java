package com.auctionapp.repository;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuctionRepository extends JpaRepository<Auction, Long> {
    Auction findByProduct(Product product);
}
