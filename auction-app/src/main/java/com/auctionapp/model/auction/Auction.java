package com.auctionapp.model.auction;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.user.User;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = JsonFormat.DEFAULT_LOCALE)
    private Date startTime;

    @Basic
    @Column (name = "end_time")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm", timezone = JsonFormat.DEFAULT_LOCALE)
    private Date endTime;

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
