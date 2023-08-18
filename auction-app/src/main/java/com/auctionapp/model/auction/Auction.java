package com.auctionapp.model.auction;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Auction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "type")
    private String type;

    @Basic
    @Column (name = "start_time")
    private Date startTime;

    @Basic
    @Column (name = "end_time")
    private Date endTime;

    @JoinColumn(name = "user_id")
    private Long userId;


    //bid_id missing
}
