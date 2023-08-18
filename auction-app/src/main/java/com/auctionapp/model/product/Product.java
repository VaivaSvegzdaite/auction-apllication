package com.auctionapp.model.product;

import com.auctionapp.model.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Basic
    @Column(name = "name")
    private String name;

    @Basic
    @Column(name = "starting_price")
    private double starting_price;

    @Basic
    @Column(name = "description")
    private String description;

    @Column(name = "category")
    private EProductCategory category;

    @ManyToOne
    @JoinColumn(name="user_id", referencedColumnName = "id")
    private User user;

    @Basic
    @Column(name = "url")
    private String url;

}

