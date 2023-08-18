package com.auctionapp.model.product;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private double starting_price;
    private String description;
    private String category;
    private Long userId;
}
