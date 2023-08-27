package com.auctionapp.controller;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.bid.BidPriceDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.user.User;
import com.auctionapp.service.BidService;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BidControllerTest {

        @Mock
        private BidService bidService;
        @Mock
        private UserService userService;

        @Mock
        private ProductService productService;

        @InjectMocks
        private BidController bidController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        @DisplayName("Checks if correctly returns a response and a body containing the list of BidDTO objects")
        public void getAllBids_ReturnsListOfBids() {
            List<BidDTO> bidList = Collections.singletonList(new BidDTO());

            //Mockito's when() method: bidService.findAll() method is mocked to return the bidList.
            when(bidService.findAll()).thenReturn(bidList);

            ResponseEntity<List<BidDTO>> response = bidController.getAllBids();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(bidList, response.getBody());
        }

        @Test
        @DisplayName("Checks if method is called with a product ID, correctly returns a response and a body contains the list of BidDTO objects for that product")
        public void getBidsByProduct_ReturnsListOfBids() {
            long productId = 1L;
            List<BidDTO> bidList = Collections.singletonList(new BidDTO());

            when(bidService.findByProductId(productId)).thenReturn(bidList);

            ResponseEntity<List<BidDTO>> response = bidController.getBidsByProduct(productId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(bidList, response.getBody());
        }

        @Test
        @DisplayName("Checks the response and if body containing the BidDTO object with the corresponding bid ID")
        public void getBidById_ExistingBid_ReturnsBid() {
            long bidId = 1L;
            BidDTO bid = new BidDTO();
            bid.setId(bidId);

            when(bidService.findById(bidId)).thenReturn(bid);

            ResponseEntity<BidDTO> response = bidController.getBidById(bidId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(bid, response.getBody());
        }

        @Test
        @DisplayName("Checks if method called with a bid ID that does not exist, it correctly returns a response and a null body, indicating that the bid was not found")
        public void getBidById_NonexistentBid_ReturnsNotFound() {
            long bidId = 1L;

            when(bidService.findById(bidId)).thenReturn(null);

            ResponseEntity<BidDTO> response = bidController.getBidById(bidId);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
        }

        @Test
        public void createBid_ExistingUser_ExistingProduct_ReturnsBid() {
            long userId = 1L;
            long productId = 1L;
            BidDTO bidDTO = new BidDTO();
            bidDTO.setUserId(userId);
            bidDTO.setProductId(productId);
            User user = new User();
            Product product = new Product();

            when(userService.getUserById(userId)).thenReturn(Optional.of(user));
            when(productService.getProductById(productId)).thenReturn(Optional.of(product));

            Bid bid = new Bid();
            when(bidService.create(bidDTO)).thenReturn(bid);

            ResponseEntity<?> response = bidController.createBid(bidDTO);
            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(bid,response.getBody());
        }

    @Test
    public void createBid_NonExistingUser_ExistingProduct_ReturnsUserNotFound() {
        long userId = 1L;
        long productId = 1L;
        BidDTO bidDTO = new BidDTO();
        bidDTO.setProductId(productId);
        User user = new User();
        Product product = new Product();

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        Bid bid = new Bid();
        when(bidService.create(bidDTO)).thenReturn(bid);

        ResponseEntity<?> response = bidController.createBid(bidDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User for bidding doesn't exist", response.getBody());
    }

    @Test
    public void createBid_ExistingUser_NonExistingProduct_ReturnsProductNotFound() {
        long userId = 1L;
        long productId = 1L;
        BidDTO bidDTO = new BidDTO();
        bidDTO.setUserId(userId);
        User user = new User();
        Product product = new Product();

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(productService.getProductById(productId)).thenReturn(Optional.of(product));

        Bid bid = new Bid();
        when(bidService.create(bidDTO)).thenReturn(bid);

        ResponseEntity<?> response = bidController.createBid(bidDTO);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Product for bidding doesn't exist", response.getBody());
    }

        @Test
        public void updateBid_ExistingBid_ReturnsSuccessMessage() {
            long bidId = 1L;
            BidPriceDTO bidPrice = new BidPriceDTO();

            when(bidService.findById(bidId)).thenReturn(new BidDTO());

            ResponseEntity<String> response = bidController.updateBid(bidId, bidPrice);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().contains("Bid price updated successfully"));
        }

        @Test
        public void updateBid_NonexistentBid_ReturnsNotFound() {
            long bidId = 1L;
            BidPriceDTO bidPrice = new BidPriceDTO();

            when(bidService.findById(bidId)).thenReturn(null);

            ResponseEntity<String> response = bidController.updateBid(bidId, bidPrice);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertTrue(response.getBody().contains("Bid doesn't exist"));
        }

        @Test
        public void deleteBid_ExistingBid_ReturnsSuccessMessage() {
            long bidId = 1L;

            when(bidService.findById(bidId)).thenReturn(new BidDTO());

            ResponseEntity<String> response = bidController.deleteBid(bidId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertTrue(response.getBody().contains("Bid was deleted successfully"));
        }

        @Test
        public void deleteBid_NonexistentBid_ReturnsNotFound() {
            long bidId = 1L;

            when(bidService.findById(bidId)).thenReturn(null);

            ResponseEntity<String> response = bidController.deleteBid(bidId);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertTrue(response.getBody().contains("Bid not found"));
        }
    }