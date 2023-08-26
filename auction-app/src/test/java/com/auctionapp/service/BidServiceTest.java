package com.auctionapp.service;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.repository.BidRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BidServiceTest {


    @Mock
    private BidRepository bidRepository;

    @Mock
    private ProductService productService;

    @InjectMocks
    private BidService bidService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void findAll_ReturnsListOfBids() {
        List<Bid> bidList = Collections.singletonList(new Bid());
        when(bidRepository.findAll()).thenReturn(bidList);

        List<BidDTO> result = bidService.findAll();

        assertEquals(1, result.size());
    }

    @Test
    public void findByProductId_ExistingProduct_ReturnsListOfBids() {
        long productId = 1L;
        Product product = new Product();
        product.setId(productId);

        when(productService.getProductById(productId)).thenReturn(Optional.of(product));
        when(bidRepository.findByProduct(product)).thenReturn(Collections.singletonList(new Bid()));

        List<BidDTO> result = bidService.findByProductId(productId);

        assertEquals(1, result.size());
    }

    @Test
    public void findById_ExistingBid_ReturnsBidDTO() {
        long bidId = 1L;
        Bid bid = new Bid();
        bid.setId(bidId);

        when(bidRepository.findById(bidId)).thenReturn(Optional.of(bid));

        BidDTO result = bidService.findById(bidId);

        assertEquals(bidId, result.getId());
    }

    @Test
    public void findById_NonexistentBid_ReturnsNull() {
        long bidId = 1L;

        when(bidRepository.findById(bidId)).thenReturn(Optional.empty());

        BidDTO result = bidService.findById(bidId);

        assertEquals(null, result);
    }

//    @Test
//    public void create_ValidBid_ReturnsBidDTO() {
//        Bid bid = new Bid();
//        when(bidRepository.save(bid)).thenReturn(bid);
//
//        BidDTO result = bidService.create(bid);
//
//        assertEquals(bid.getId(), result.getId());
//    }

    @Test
    public void deleteById_ExistingBid_DeletesBid() {
        long bidId = 1L;

        bidService.deleteById(bidId);

        verify(bidRepository, times(1)).deleteById(bidId);
    }

}