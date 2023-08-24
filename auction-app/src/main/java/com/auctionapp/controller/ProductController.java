package com.auctionapp.controller;

import com.auctionapp.model.product.Product;
import com.auctionapp.model.product.ProductDTO;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
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
        List<ProductDTO> listOfProductResponses = productService.convertToDTOList(products);
        return ResponseEntity.ok(listOfProductResponses);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<Product> oProduct = productService.getProductById(id);
        return oProduct.map(product -> ResponseEntity.ok(productService.convertToDTO(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<?> createProduct(@RequestBody @Valid Product product, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.badRequest().
                    body(bindingResult.
                            getAllErrors().
                            stream().
                            map(ObjectError::getDefaultMessage).
                            collect(Collectors.joining()));
        }
        var response = productService.createProduct(product);
        return response;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        if (id == null || productDTO.getUserId() == null) {
            return ResponseEntity.badRequest().body("Invalid request data!");
        }
        var oExistingProduct = productService.getProductById(id);
        if (oExistingProduct.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product with id nr. " + id + " is not found");
        }
        var existingProduct = oExistingProduct.get();

        try {
            productService.updateProduct(existingProduct, productDTO);
            return ResponseEntity.ok("Product was updated successfully");
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
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
}