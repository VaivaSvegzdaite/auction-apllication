package com.auctionapp.model.auction;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
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
    private LocalDateTime startTime;

    @Basic
    @Column (name = "end_time")
    private LocalDateTime endTime;

    @Basic
    @Column(name = "starting_price")
    @Min(value = 1, message = "Price should not be less than 1 EUR")
    private double startingPrice;

    @ManyToOne
    @JoinColumn(name="product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @OneToMany
    @JoinColumn(name="auction_id", referencedColumnName = "id") //this column will be in table bid
    private List<Bid> bids;

}
