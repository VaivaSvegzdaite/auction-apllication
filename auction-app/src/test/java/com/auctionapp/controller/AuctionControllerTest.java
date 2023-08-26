package com.auctionapp.controller;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.user.User;
import com.auctionapp.service.AuctionService;
import com.auctionapp.service.BidService;
import com.auctionapp.service.ProductService;
import com.auctionapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class AuctionControllerTest {

    @Mock
    private AuctionService auctionService;

    @Mock
    private UserService userService;

    @Mock
    private BidService bidService;

    @Mock
    private ProductService productService;

    @Mock
    private AuctionController auctionController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        auctionController = new AuctionController(auctionService, userService, bidService, productService);
    }


    @Test
    public void getAllAuctions_ReturnsListOfAuctions() {
        List<Auction> auctionList = new ArrayList<>();
        auctionList.add(new Auction());

        when(auctionService.getAllAuctions()).thenReturn(auctionList);

        ResponseEntity<List<Auction>> response = auctionController.getAllAuctions();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionList, response.getBody());
    }

    @Test
    public void getAuctionByProductId_ExistingProduct_ReturnsAuction() {
        long productId = 1L;
        AuctionDTO auctionDTO = new AuctionDTO();

        when(auctionService.getAuctionByProductId(productId)).thenReturn(auctionDTO);
        ResponseEntity<AuctionDTO> response = auctionController.getAuctionByProductId(productId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionDTO, response.getBody());
    }

    @Test
    public void getAuctionByProductId_NonexistentProduct_ReturnsNotFound() {
        long productId = 1L;

        when(auctionService.getAuctionByProductId(productId)).thenReturn(null);

        ResponseEntity<AuctionDTO> response = auctionController.getAuctionByProductId(productId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAuctionById_ExistingAuction_ReturnsAuction() {
        long auctionId = 1L;
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auctionId);

        when(auctionService.getAuctionById(auctionId)).thenReturn(auctionDTO);

        ResponseEntity<AuctionDTO> response = auctionController.getAuctionById(auctionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionDTO, response.getBody());
    }

    @Test
    public void getAuctionById_NonexistentAuction_ReturnsNotFound() {
        long auctionId = 1L;

        when(auctionService.getAuctionById(auctionId)).thenReturn(null);

        ResponseEntity<AuctionDTO> response = auctionController.getAuctionById(auctionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void createAuction_InvalidAuctionId_ReturnsBadRequest() {
        Auction auction = new Auction();
        auction.setId(1L); // Setting a non-null ID to indicate an existing auction

        ResponseEntity<?> response = auctionController.createAuction(auction);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }

    @Test
    public void createAuction_UserNotFound_ReturnsNotFound() {
        Auction auction = new Auction();
        auction.setUser(new User()); // Assume user doesn't exist
        when(userService.getUserById(auction.getUser().getId())).thenReturn(Optional.empty());

        ResponseEntity<?> response = auctionController.createAuction(auction);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("User for adding auction doesn't exist", response.getBody());
    }

    @Test
    public void updateAuction_AuctionExists_ReturnsUpdatedAuction() {
        long auctionId = 1L;
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auctionId);

        when(auctionService.getAuctionById(auctionId)).thenReturn(auctionDTO);
        //when(auctionService.updateAuction(auctionId, auctionDTO)).thenReturn(auctionDTO);

        ResponseEntity<?> response = auctionController.updateAuction(auctionId, auctionDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(auctionDTO, response.getBody());
    }

    @Test
    public void updateAuction_AuctionDoesNotExist_ReturnsNotFound() {
        long auctionId = 1L;

        when(auctionService.getAuctionById(auctionId)).thenReturn(null);

        ResponseEntity<?> response = auctionController.updateAuction(auctionId, new AuctionDTO());

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Auction doesn't exist", response.getBody());
    }

    @Test
    public void deleteAuction_AuctionExists_ReturnsSuccessMessage() {
        long auctionId = 1L;
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auctionId);

        when(auctionService.getAuctionById(auctionId)).thenReturn(auctionDTO);

        ResponseEntity<String> response = auctionController.deleteAuction(auctionId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Auction was deleted successfully!", response.getBody());
    }

    @Test
    public void deleteAuction_AuctionDoesNotExist_ReturnsNotFound() {
        long auctionId = 1L;

        when(auctionService.getAuctionById(auctionId)).thenReturn(null);

        ResponseEntity<String> response = auctionController.deleteAuction(auctionId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("Auction for deletion doesn't exist!", response.getBody());
    }

}