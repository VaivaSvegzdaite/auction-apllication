package com.auctionapp.model.auction;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class AuctionDTO {
    private Long id;
    private String type;
    private Date startTime;
    private Date endTime;
    private Long userId;
    private List<Long> bidId;
}
