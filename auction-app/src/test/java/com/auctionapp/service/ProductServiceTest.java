package com.auctionapp.service;

import com.auctionapp.model.product.EProductCategory;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.product.ProductDTO;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {
    private ProductService productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private UserService userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productRepository, userService);
    }

    @Test
    @DisplayName("Checks behavior of the createProduct method in the ProductService class when an invalid user is associated with the product")
    public void createProduct_InvalidUser_ReturnsNotFoundResponse() {
        Product product = new Product();
        product.setName("Product Name");
        product.setUser(new User());
        product.getUser().setId(1L);

        when(userService.getUserById(product.getUser().getId())).thenReturn(Optional.empty());

        ResponseEntity<String> response = productService.createProduct(product);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User for adding product doesnt exist", response.getBody());
    }

    @Test
    @DisplayName("Checks that getAllProducts method properly interacts with the mocked productRepository and returns the expected list of products")
    public void getAllProducts_ReturnsListOfProducts() {
        List<Product> productList = Collections.singletonList(new Product());

        when(productRepository.findAll()).thenReturn(productList);

        List<Product> result = productService.getAllProducts();

        assertEquals(productList, result);
    }

    @Test
    @DisplayName("Checks if properly interacts with the mocked productRepository and returns the expected product for a given existing product ID")
    public void getProductById_ExistingProductId_ReturnsProduct() {
        Long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productRepository.findById(productId)).thenReturn(Optional.of(product));

        Optional<Product> result = productService.getProductById(productId);

        assertTrue(result.isPresent());
        assertEquals(productId, result.get().getId());
    }

    @Test
    @DisplayName("Checks if getProductById method properly interacts with the mocked productRepository and returns an empty Optional for a nonexistent product ID")
    public void getProductById_NonexistentProductId_ReturnsEmptyOptional() {
        Long productId = 1L;

        when(productRepository.findById(productId)).thenReturn(Optional.empty());

        Optional<Product> result = productService.getProductById(productId);

        assertFalse(result.isPresent());
    }
    @Test
    @DisplayName("Checks if method correctly updates the properties of an existing product and calls the save method of the repository to persist the changes")
    public void testUpdateProduct() {
        User user = new User();
        user.setId(1L);

        // Create a mock existingProduct
        Product existingProduct = new Product();
        existingProduct.setUser(user);

        ProductDTO productDTO = new ProductDTO();
        productDTO.setName("New Name");
        productDTO.setDescription("New Description");
        productDTO.setCategory("ELECTRONICS");
        productDTO.setUserId(1L);
        productDTO.setUrl("https://example.com");

        when(userService.getUserById(productDTO.getUserId())).thenReturn(Optional.of(user));

        productService.updateProduct(existingProduct, productDTO);

        assertEquals("New Name", existingProduct.getName());
        assertEquals("New Description", existingProduct.getDescription());
        assertEquals(EProductCategory.ELECTRONICS, existingProduct.getCategory());
        assertEquals("https://example.com", existingProduct.getUrl());
        assertEquals(user, existingProduct.getUser());

        verify(productRepository, times(1)).save(existingProduct);
    }

    @Test
    @DisplayName("Checks if method interacts correctly with the mocked productRepository and deletes an existing product when the appropriate conditions are met")
    public void deleteProduct_ExistingProduct_DeletesProduct() {
        Long productId = 1L;

        when(productRepository.existsById(productId)).thenReturn(true);

        productService.deleteProduct(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

    @Test
    @DisplayName("Checks if method correctly converts a mock Product instance to a ProductDTO instance with the expected attributes.")
    public void convertToDTO_ConvertsProductToDTO() {
        Long productId = 1L;
        String productName = "Test Product";
        double startingPrice = 10.0;
        String description = "This is a test product";
        EProductCategory category = EProductCategory.ELECTRONICS;
        String url = "http://example.com/product";
        Long userId = 2L;

        User user = new User();
        user.setId(userId);

        Product product = new Product();
        product.setId(productId);
        product.setName(productName);
        product.setDescription(description);
        product.setCategory(category);
        product.setUrl(url);
        product.setUser(user);

        ProductDTO productDTO = productService.convertToDTO(product);

        assertEquals(productId, productDTO.getId());
        assertEquals(productName, productDTO.getName());
        assertEquals(description, productDTO.getDescription());
        assertEquals(category.toString(), productDTO.getCategory());
        assertEquals(url, productDTO.getUrl());
        assertEquals(userId, productDTO.getUserId());
    }

    @Test
    public void convertToDTOList_ConvertsListOfProductsToDTOList() {
        Long productId1 = 1L;
        Long productId2 = 2L;

        Product product1 = new Product();
        product1.setId(productId1);
        product1.setName("Product 1");
        product1.setDescription("Description 1");
        product1.setCategory(EProductCategory.ELECTRONICS);
        product1.setUrl("http://example.com/product1");
        product1.setUser(new User());
        product1.getUser().setId(1L);

        Product product2 = new Product();
        product2.setId(productId2);
        product2.setName("Product 2");
        product2.setDescription("Description 2");
        product2.setCategory(EProductCategory.CLOTHING);
        product2.setUrl("http://example.com/product2");
        product2.setUser(new User());
        product2.getUser().setId(2L);

        List<Product> productList = Arrays.asList(product1, product2);

        List<ProductDTO> productDTOList = productService.convertToDTOList(productList);

        assertEquals(2, productDTOList.size());

        ProductDTO dto1 = productDTOList.get(0);
        assertEquals(productId1, dto1.getId());
        assertEquals("Product 1", dto1.getName());
        assertEquals("Description 1", dto1.getDescription());
        assertEquals(EProductCategory.ELECTRONICS.toString(), dto1.getCategory());
        assertEquals("http://example.com/product1", dto1.getUrl());
        assertEquals(1L, dto1.getUserId());

        ProductDTO dto2 = productDTOList.get(1);
        assertEquals(productId2, dto2.getId());
        assertEquals("Product 2", dto2.getName());
        assertEquals("Description 2", dto2.getDescription());
        assertEquals(EProductCategory.CLOTHING.toString(), dto2.getCategory());
        assertEquals("http://example.com/product2", dto2.getUrl());
        assertEquals(2L, dto2.getUserId());
    }

}
