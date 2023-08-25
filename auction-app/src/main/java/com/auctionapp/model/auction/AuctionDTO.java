package com.auctionapp.model.auction;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class AuctionDTO {
    private Long id;
    private EAuctionType type;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double startingPrice;
    private Long productId;
    private Long userId;
    private List<Long> bidId;
}
