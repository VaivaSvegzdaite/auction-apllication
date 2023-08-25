package com.auctionapp.service;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.repository.AuctionRepository;
import com.auctionapp.repository.BidRepository;
import com.auctionapp.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;
    private AuctionService auctionService;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks
        auctionService = new AuctionService(auctionRepository, productService);
    }

    @Test
    @DisplayName("Test for getting all auctions request behavior")
    public void testGetAllAuctions() {
        List<Auction> auctionList = new ArrayList<>();
        when(auctionRepository.findAll()).thenReturn(auctionList);

        List<Auction> result = auctionService.getAllAuctions();
        assertEquals(auctionList, result);
    }

    @Test
    @DisplayName("Test for creating auction request behavior")
    public void testCreateAuction() {
        Auction auction = new Auction();
        auctionService.createAuction(auction);
        verify(auctionRepository, times(1)).save(auction);
    }

    @Test
    @DisplayName("Test for deleting auction request behavior")
    public void testDeleteAuction() {
        Long auctionId = 1L;
        auctionService.deleteAuction(auctionId);
        verify(auctionRepository, times(1)).deleteById(auctionId);
    }
}
