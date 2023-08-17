package com.auctionapp.product.model;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import com.auctionapp.login.model.User;

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
  private ECategory category;

  @JoinColumn(name = "user_id")
  private Long userId;

    public Product(String name, double starting_price, String description, Long userId, ECategory category) {
   this.name = name;
   this.starting_price = starting_price;
   this.description = description;
   this.userId = userId;
   this.category = category;
 }

    }

