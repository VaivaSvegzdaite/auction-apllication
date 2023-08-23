package com.auctionapp.model.product;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    @DisplayName("Test Getters and Setters of Product Class")
    public void testGettersAndSetters() {
        Product product = new Product();

        product.setId(1L);
        assertEquals(1L, product.getId());

        product.setName("Example Product");
        assertEquals("Example Product", product.getName());

        product.setDescription("Description for testing.");
        assertEquals("Description for testing.", product.getDescription());

        product.setCategory(EProductCategory.ELECTRONICS);
        assertEquals(EProductCategory.ELECTRONICS, product.getCategory());

        product.setUrl("https://example.com/product-url");
        assertEquals("https://example.com/product-url", product.getUrl());
    }

}