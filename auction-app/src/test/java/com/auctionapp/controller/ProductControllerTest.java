package com.auctionapp.controller;

import com.auctionapp.model.product.Product;
import com.auctionapp.model.product.ProductDTO;
import com.auctionapp.model.user.User;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    private ProductController productController;

    @Mock
    private ProductService productService;

    @Mock
    private UserService userService;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productController = new ProductController(productService, userService);
    }

    @Test
    public void createProduct_InvalidProduct_ReturnsBadRequest() {
        Product product = new Product();

        when(bindingResult.hasErrors()).thenReturn(true);

        ResponseEntity<?> response = productController.createProduct(product, bindingResult);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void getProductById_ExistingProductId_ReturnsProductDTO() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(productId);

        when(productService.getProductById(productId)).thenReturn(Optional.of(new Product()));
        when(productService.convertToDTO(any())).thenReturn(productDTO);

        ResponseEntity<?> response = productController.getProductById(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(productId, Objects.requireNonNull(response.getBody()).getId());
    }

    @Test
    public void getProductById_NonexistentProductId_ReturnsNotFound() {
        Long productId = 1L;

        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        ResponseEntity<?> response = productController.getProductById(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void updateProduct_ExistingProduct_ReturnsSuccessResponse() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setUserId(1L);

        Product existingProduct = new Product();
        existingProduct.setId(productId);
        existingProduct.setUser(new User()); // Assuming you have a User instance

        when(productService.getProductById(productId)).thenReturn(Optional.of(existingProduct));

        ResponseEntity<String> response = productController.updateProduct(productId, productDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product was updated successfully", response.getBody());
    }

    @Test
    public void updateProduct_NonexistentProduct_ReturnsNotFound() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        productDTO.setUserId(1L);

        when(productService.getProductById(productId)).thenReturn(Optional.empty());

        ResponseEntity<String> response = productController.updateProduct(productId, productDTO);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void deleteProduct_ExistingProduct_ReturnsSuccessResponse() {
        Long productId = 1L;

        when(productService.getProductById(productId)).thenReturn(Optional.of(new Product()));

        ResponseEntity<String> response = productController.deleteProduct(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Product was deleted successfully!", response.getBody());
    }

    @Test
    public void getAllProducts_ReturnsListOfProducts() {
        List<Product> productList = Collections.singletonList(new Product());

        when(productService.getAllProducts()).thenReturn(productList);
        when(productService.convertToDTOList(productList)).thenReturn(Collections.singletonList(new ProductDTO()));

        ResponseEntity<List<Product>> response = productController.getAllProducts();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, Objects.requireNonNull(response.getBody()).size());
    }
}