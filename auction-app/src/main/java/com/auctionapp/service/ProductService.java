package com.auctionapp.service;

import com.auctionapp.model.product.EProductCategory;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.product.ProductDTO;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, UserService userService) {
        this.productRepository = productRepository;
        this.userService = userService;
    }

    @Transactional
    public ResponseEntity<?> createProduct(Product product) {
        logger.info("Creating product");
        if (product.getId() != null && product.getId() != 0) {
            return ResponseEntity.badRequest().body("Invalid product ID");
        }
        Long userId = product.getUser().getId();
        Optional<User> user = userService.getUserById(userId);
        if (user.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for adding product doesnt exist");
        }
        productRepository.save(product);
        logger.info("Product created successfully with ID: {}", product.getId());
        return ResponseEntity.ok(convertToDTO(product));
    }

    @Transactional
    public List<Product> getAllProducts() {
        logger.info("Getting all products");
        return productRepository.findAll();
    }

    @Transactional
    public List<ProductDTO> getProductsByUserId(Long userId) {
        logger.info("Getting products by user ID: {}", userId);
        List<ProductDTO> allProducts = convertToDTOList(productRepository.findAll());
        List<ProductDTO> productsByUserId = new ArrayList<ProductDTO>();
        for (ProductDTO product : allProducts) {
            if (product.getUserId() == userId)
                productsByUserId.add(product);
        }
        return productsByUserId;
    }

    @Transactional
    public Optional<Product> getProductById(Long id) {
        logger.info("Getting product by ID: {}", id);
        return productRepository.findById(id);
    }

    @Transactional
    public void updateProduct(Product existingProduct, ProductDTO productDTO) {
        logger.info("Updating product with ID: {}", existingProduct.getId());
        existingProduct.setName(productDTO.getName());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(EProductCategory.valueOf(productDTO.getCategory()));
        existingProduct.setUrl(productDTO.getUrl());

        Long userId = productDTO.getUserId();
        if (userService.getUserById(userId).isPresent()) {
            existingProduct.getUser().setId(productDTO.getUserId());
            productRepository.save(existingProduct);
            logger.info("Product with ID {} updated successfully", existingProduct.getId());
        } else {
            throw new UsernameNotFoundException("User for updating product doesnt exist");
        }
    }

    @Transactional
    public void deleteProduct(Long id) {
        logger.info("Deleting product with ID: {}", id);
        productRepository.deleteById(id);
        logger.info("Product with ID {} deleted successfully", id);
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
