package com.auctionapp.service;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.auction.EAuctionType;
import com.auctionapp.model.product.Product;
import com.auctionapp.repository.AuctionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@Transactional(propagation = Propagation.NOT_SUPPORTED)

public class AuctionServiceTest {

    @Mock
    private AuctionRepository auctionRepository;
    @Mock
    private AuctionService auctionService;
    @Mock
    private UserService userService;

    @Mock
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
        verify(auctionRepository, times(1)).findAll();
        assertEquals(auctionList, result);
    }

    @Test
    @DisplayName("Test for getting auctions by productId request behavior")
    public void testGetAuctionsByProductId() {

        long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        Auction auction = new Auction();

        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(auctionRepository.findByProduct(product)).thenReturn(auction);

        Auction result = auctionService.getAuctionByProductId(productId);

        verify(auctionRepository, times(1)).findByProduct(product);
    }

    @Test
    @DisplayName("Test for getting auctions by auction ID request behavior")
    public void testGetAuctionById() {
        Long auctionId = 1L;
        Auction auction = new Auction();
        auction.setId(auctionId);
        auction.setType(EAuctionType.STANDARD);

        when(auctionRepository.findById(auctionId)).thenReturn(Optional.of(auction));

        Auction result = auctionService.getAuctionById(auctionId);
        assertNotNull(result);
        assertEquals(auctionId, result.getId());
    }

    @Test
    @DisplayName("Test for creating auction request behavior")
    public void testCreateAuction() {
        Auction auction = new Auction();
        auction.setId(1L);

        auctionService.createAuction(auction);

        verify(auctionRepository, times(1)).save(auction);
    }


    @Test
    @DisplayName("Test for deleting auction request behavior")
    public void testDeleteAuction() {
        Long auctionId = 1L;
        Auction toAdd = new Auction();
        toAdd.setId(auctionId);
        auctionService.createAuction(toAdd);

        auctionService.deleteAuction(auctionId);
        verify(auctionRepository, times(1)).deleteById(auctionId);
    }
    @Test
    @DisplayName("Test for updating auction request behavior")
    public void testUpdate()
    {
        Long auctionId = 3L;
        when(auctionRepository.findById(auctionId)).thenReturn(Optional.empty());
        auctionService.updateAuction(auctionId, new AuctionDTO());
        verify(auctionRepository, never()).save(new Auction());
    }

}