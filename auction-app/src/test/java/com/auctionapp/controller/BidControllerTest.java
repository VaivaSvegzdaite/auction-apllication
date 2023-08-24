package com.auctionapp.controller;

import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.bid.BidPriceDTO;
import com.auctionapp.service.BidService;
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
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class BidControllerTest {

        @Mock
        private BidService bidService;

        @InjectMocks
        private BidController bidController;

        @BeforeEach
        public void setUp() {
            MockitoAnnotations.openMocks(this);
        }

        @Test
        @DisplayName("Checks if correctly returns a response with an HTTP status of OK and a body containing the list of BidDTO objects")
        public void getAllBids_ReturnsListOfBids() {
            List<BidDTO> bidList = Collections.singletonList(new BidDTO());

            //Mockito's when() method: bidService.findAll() method is mocked to return the bidList.
            when(bidService.findAll()).thenReturn(bidList);

            ResponseEntity<List<BidDTO>> response = bidController.getAllBids();

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(bidList, response.getBody());
        }

        @Test
        public void getBidsByProduct_ReturnsListOfBids() {
            long productId = 1L;
            List<BidDTO> bidList = Collections.singletonList(new BidDTO());

            when(bidService.findByProductId(productId)).thenReturn(bidList);

            ResponseEntity<List<BidDTO>> response = bidController.getBidsByProduct(productId);

            assertEquals(HttpStatus.OK, response.getStatusCode());
            assertEquals(bidList, response.getBody());
        }

        @Test
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
        public void getBidById_NonexistentBid_ReturnsNotFound() {
            long bidId = 1L;

            when(bidService.findById(bidId)).thenReturn(null);

            ResponseEntity<BidDTO> response = bidController.getBidById(bidId);

            assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
            assertNull(response.getBody());
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