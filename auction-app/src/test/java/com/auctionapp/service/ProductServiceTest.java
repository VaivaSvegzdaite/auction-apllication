package com.auctionapp.service;

import com.auctionapp.model.product.Product;
import com.auctionapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        productService = new ProductService(productRepository);
    }

    @Test
    @DisplayName("Test for getting all products request behavior")
    public void testGetAllProducts() {
        List<Product> productList = new ArrayList<>();
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();
        assertEquals(productList, result);
    }

    @Test
    @DisplayName("Test for getting products by ID request behavior")
    public void testGetProductById() {
        Long productId = 1L;
        Product product = new Product();
        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);
        assertEquals(Optional.of(product), result);
    }

    @Test
    @DisplayName("Test for creating product request behavior")
    public void testCreateProduct() {
        Product product = new Product();
        productService.createProduct(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Test for updating product request behavior")
    public void testUpdateProduct() {
        Product product = new Product();
        productService.updateProduct(product);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    @DisplayName("Test for deleting product request behavior")
    public void testDeleteProduct() {
        Long productId = 1L;
        productService.deleteProduct(productId);
        verify(productRepository, times(1)).deleteById(productId);
    }

}
