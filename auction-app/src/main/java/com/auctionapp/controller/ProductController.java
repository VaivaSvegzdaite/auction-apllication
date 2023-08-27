package com.auctionapp.controller;

import com.auctionapp.model.product.Product;
import com.auctionapp.model.product.ProductDTO;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@Slf4j
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private static final Logger logger = LoggerFactory.getLogger(ProductController.class);
    public final ProductService productService;
    public final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Product>> getAllProducts() {
        logger.info("getAllProducts method called.");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        logger.info("getProductById method called with id: {}", id);
        Optional<Product> oProduct = productService.getProductById(id);
        return oProduct.map(product -> {
            logger.info("Product found with id: {}", id);
            return ResponseEntity.ok(product);
        }).orElseGet(() -> {
            logger.warn("Product not found with id: {}", id);
            return ResponseEntity.notFound().build();
        });
    }

    @GetMapping("/user/{userId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<ProductDTO>> getProductsByUserId(@PathVariable Long userId) {
        logger.info("getProductsByUserId method called for user id: {}", userId);
        List<ProductDTO> products = productService.getProductsByUserId(userId);
        return ResponseEntity.ok(products);
    }

    @PostMapping("/")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        logger.info("createProduct method called.");

        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.joining());

            logger.warn("Validation errors occurred: {}", errorMessages);
            return ResponseEntity.badRequest().body(errorMessages);
        }

        try {
            var response = productService.createProduct(product);
            logger.info("Product created successfully with id: {}", response.getBody());
            return response;
        } catch (Exception e) {
            logger.error("Error occurred while creating the product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while creating the product.");
        }
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        logger.info("updateProduct method called with id: {}", id);

        if (id == null || productDTO.getUserId() == null) {
            logger.warn("Invalid request data for updating product.");
            return ResponseEntity.badRequest().body("Invalid request data!");
        }

        var oExistingProduct = productService.getProductById(id);
        if (oExistingProduct.isEmpty()) {
            logger.warn("Product not found with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id nr. " + id + " is not found");
        }

        var existingProduct = oExistingProduct.get();

        try {
            productService.updateProduct(existingProduct, productDTO);
            logger.info("Product updated successfully with id: {}", id);
            return ResponseEntity.ok("Product was updated successfully");
        } catch (EntityNotFoundException e) {
            logger.error("Error occurred while updating the product: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        logger.info("deleteProduct method called with id: {}", id);

        if (id != null && productService.getProductById(id).isPresent()) {
            productService.deleteProduct(id);
            logger.info("Product deleted successfully with id: {}", id);
            return ResponseEntity.ok("Product was deleted successfully!");
        } else {
            logger.warn("Product not found for deletion with id: {}", id);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product for deletion doesn't exist!");
        }
    }
}
