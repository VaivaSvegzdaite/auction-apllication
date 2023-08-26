package com.auctionapp.model.bid;

import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.login.UserDTO;
import com.auctionapp.model.product.ProductDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BidDTO {
    private Long id;
    private double price;
    private Long userId;
    private Long productId;
    private Long auctionId;
}
