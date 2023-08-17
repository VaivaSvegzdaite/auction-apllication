package com.auctionapp.product.service;

import com.auctionapp.product.model.Product;
import com.auctionapp.product.repository.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductService {
    private static ProductRepo productRepo;

    @Autowired
    public ProductService(ProductRepo productRepo) {
        this.productRepo = productRepo;
    }

    public static Product createProduct(Product product) {
        return productRepo.save(product);
    }

    public static List<Product>getAllProducts() {
        return productRepo.findAll();
    }

    public static Product updateProduct(Product product) {
        return productRepo.save(product);
    }

    public static void deleteProduct(Long id) {
        productRepo.deleteById(id);
    }
}
