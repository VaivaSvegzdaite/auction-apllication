package com.auctionapp.service;

import com.auctionapp.model.product.Product;
import com.auctionapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    private static ProductRepository productRepository;

    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public static Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public static List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public static Product updateProduct(Product product) {
        return productRepository.save(product);
    }

    public static void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
