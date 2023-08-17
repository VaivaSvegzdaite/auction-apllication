package com.auctionapp.model.bid;

import com.auctionapp.model.product.Product;
import com.auctionapp.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name="bid")
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "price")
    private double price;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @ManyToOne
    @JoinColumn(name="product_id", referencedColumnName = "id")
    private Product product;

    public Bid(double price, User user, Product product) {
        this.price = price;
        this.user = user;
        this.product = product;
    }
}
