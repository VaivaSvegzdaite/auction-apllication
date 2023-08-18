package com.auctionapp.model.bid;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class BidDTO {
    private Long id;
    private double price;
    private Long userId;
    private Long productId;
}
