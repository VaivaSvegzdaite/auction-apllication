package com.auctionapp.model.auction;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

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
    private EAuctionType type;

    @Basic
    @Column (name = "start_time")
    private Date startTime;

    @Basic
    @Column (name = "end_time")
    private Date endTime;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany
    @JoinColumn(name="auction_id", referencedColumnName = "id") //this column will be in table bid
    private List<Bid> bids;
}
