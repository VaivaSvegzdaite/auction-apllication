package com.auctionapp.model.auction;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class AuctionDTO {
    private Long id;
    private String type;
    private Date startTime;
    private Date endTime;
    private Long userId;
    //bid_id missing
}
