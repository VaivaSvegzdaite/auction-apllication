package com.auctionapp.controller;

import com.auctionapp.model.product.EProductCategory;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.product.ProductDTO;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 36000)
@RestController
@RequestMapping("/api/product")
public class ProductController {

    public final ProductService productService;

    public final UserService userService;

    public ProductController(ProductService productService, UserService userService) {
        this.productService = productService;
        this.userService = userService;
    }

    @GetMapping("/")
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<Product> products = productService.getAllProducts();
        List<ProductDTO> listOfProductResponses = convertToDTOList(products);
        return ResponseEntity.ok(listOfProductResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<Product> oProduct = productService.getProductById(id);
        return oProduct.map(product -> ResponseEntity.ok(convertToDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<String> createProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(bindingResult.
                            getAllErrors().
                            stream().
                            map(ObjectError::getDefaultMessage).
                            collect(Collectors.joining()));
        }
        if (product.getId() != null && product.getId() != 0) {
            return ResponseEntity.badRequest().build();
        }
        Long userId = product.getUser().getId();
        if (userService.getUserById(userId).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for adding product doesn't exist");
        }
        product.setUrl(product.getUrl());
        productService.createProduct(product);
        return ResponseEntity.ok("Product created successfully!");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        if (id == null || productDTO.getUserId() == null) {
            return ResponseEntity.badRequest().body("Invalid request data!");
        }
        var oExistingProduct = productService.getProductById(id);
        if (oExistingProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        var existingProduct = oExistingProduct.get();
        existingProduct.setName(productDTO.getName());
        existingProduct.setStarting_price(productDTO.getStarting_price());
        existingProduct.setDescription(productDTO.getDescription());
        existingProduct.setCategory(EProductCategory.valueOf(productDTO.getCategory()));
        existingProduct.setUrl(productDTO.getUrl());
        Long userId = productDTO.getUserId();
        if (userService.getUserById(userId).isPresent()) {
            existingProduct.getUser().setId(productDTO.getUserId());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User for updating product doesn't exist!");
        }
        productService.updateProduct(existingProduct);
        return ResponseEntity.ok("Product was updated successfully!");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
        if (id != null && productService.getProductById(id).isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product was deleted successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product for deletion doesn't exist!");
        }
    }

    private ProductDTO convertToDTO(Product product) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setStarting_price(product.getStarting_price());
        productDTO.setDescription(product.getDescription());
        productDTO.setCategory(String.valueOf(product.getCategory()));
        productDTO.setUrl(product.getUrl());
        if (product.getUser() != null) {
            productDTO.setUserId(product.getUser().getId());
        }
        return productDTO;
    }

    private List<ProductDTO> convertToDTOList(List<Product> products) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        for (Product product : products) {
            productDTOList.add(convertToDTO(product));
        }
        return productDTOList;
    }

}

