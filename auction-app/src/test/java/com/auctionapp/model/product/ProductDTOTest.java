package com.auctionapp.model.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ProductDTOTest {

    @Test
    @DisplayName("Test Getters and Setters of ProductDTO Class")
    public void testGettersAndSetters() {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setId(1L);
        assertEquals(1L, productDTO.getId());

        productDTO.setName("Example Product");
        assertEquals("Example Product", productDTO.getName());

        productDTO.setStarting_price(100.0);
        assertEquals(100.0, productDTO.getStarting_price(), 0.001);

        productDTO.setDescription("Description for testing.");
        assertEquals("Description for testing.", productDTO.getDescription());

        productDTO.setCategory("Electronics");
        assertEquals("Electronics", productDTO.getCategory());

        productDTO.setUserId(2L);
        assertEquals(2L, productDTO.getUserId());

        productDTO.setUrl("https://example.com/product-url");
        assertEquals("https://example.com/product-url", productDTO.getUrl());
    }
}