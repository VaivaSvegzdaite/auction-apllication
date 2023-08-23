package com.auctionapp.service;

import com.auctionapp.model.product.EProductCategory;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.product.ProductDTO;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.ProductRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public ResponseEntity<String> createProduct(Product product) {
        if (product.getId() != null && product.getId() != 0) {
            return ResponseEntity.badRequest().body("Invalid product ID");
        }
        Long userId = product.getUser().getId();
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for adding product doesnt exist");
        }
        productRepository.save(product);
        return ResponseEntity.ok("Product created successfully");
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }

    public List<ProductDTO> getProductByName(String name) {
        List<Product> products = productRepository.findByAllNames(name);

        /*if (products.isEmpty()) {
            products = productRepository.findByAllNames(name);
        }*/

        if (products.isEmpty()) {
            throw new RuntimeException("No products found with name: " + name);
        }
        return convertToDTOList(products);
    }

    public void updateProduct(Product existingProduct, ProductDTO productDTO) {
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(EProductCategory.valueOf(productDTO.getCategory()));
        existingProduct.setUrl(productDTO.getUrl());

        Long userId = productDTO.getUserId();
        if (userService.getUserById(userId).isPresent()) {
            existingProduct.getUser().setId(productDTO.getUserId());
            productRepository.save(existingProduct);
        } else {
            throw new UsernameNotFoundException("User for updating product doesnt exist");
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(String.valueOf(product.getCategory()));
        productDTO.setUrl(product.getUrl());
        if (product.getUser() != null) {
            productDTO.setUserId(product.getUser().getId());
        }
        return productDTO;
    }

    public List<ProductDTO> convertToDTOList(List<Product> products) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            productDTOList.add(convertToDTO(product));
        }
        return productDTOList;
    }

}