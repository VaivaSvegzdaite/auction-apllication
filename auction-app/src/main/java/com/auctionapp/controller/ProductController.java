package com.auctionapp.controller;

import com.auctionapp.model.product.Product;
import com.auctionapp.repository.ProductRepository;
import com.auctionapp.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductRepository productRepository;

    public ProductController(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = ProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if (product.getId() != null && product.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }

        Product createdProduct = ProductService.createProduct(product);
        return ResponseEntity.ok(createdProduct);
    }

    @PutMapping("/update")
    public ResponseEntity<Product> updateProduct(@RequestBody Product product) {
        if (product.getId() == null || product.getId() == 0) {
            return ResponseEntity.badRequest().build();
        }
        Product updatedProduct = ProductService.updateProduct(product);
        return ResponseEntity.ok(updatedProduct);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteProduct(@RequestBody Map<String, Long> requestBody) {
        Long id = requestBody.get("id");
        if (id != null) {
            ProductService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}

