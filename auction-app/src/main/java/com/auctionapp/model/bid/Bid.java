package com.auctionapp.model.bid;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name="bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    @Min(value = 1, message = "Bid should not be less than 1 EUR")
    private double price;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    @NotNull
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id", referencedColumnName = "id")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "auction_id")
    @JsonBackReference
    private Auction auction;

    public Bid(double price, User user, Product product, Auction auction) {
        this.price = price;
        this.user = user;
        this.product = product;
        this.auction = auction;
    }
}
