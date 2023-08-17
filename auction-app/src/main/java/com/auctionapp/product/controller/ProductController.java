package com.auctionapp.product.controller;

import com.auctionapp.product.model.Product;
import com.auctionapp.product.repository.ProductRepo;
import com.auctionapp.product.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private ProductRepo productRepo;

    public ProductController(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    @GetMapping()
    public ResponseEntity<List<Product>> getAllProducts() {
        List<Product> products = ProductService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @PostMapping("/add")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        if(product.getId() != null && product.getId() != 0) {
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

