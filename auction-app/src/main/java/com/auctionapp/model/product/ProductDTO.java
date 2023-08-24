package com.auctionapp.model.product;

import jakarta.persistence.CascadeType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProductDTO {
    private Long id;
    private String name;
    private String description;
    private String category;
    private Long userId;
    private String url;
}
