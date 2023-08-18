package com.auctionapp.service;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.bid.BidPriceDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.repository.BidRepository;
import com.auctionapp.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BidService {
    private BidRepository bidRepository;
    private ProductService productService;

    @Autowired
    public BidService(BidRepository bidRepository, ProductService productService) {
        this.bidRepository = bidRepository;
        this.productService = productService;
    }

    public List<BidDTO> findAll() {
        List<Bid> bids =  bidRepository.findAll();
        List<BidDTO> listOfBidDTOs = convertToDTOList(bids);
        return listOfBidDTOs;
    }

    public List<BidDTO> findByProductId(long productId) {
        Optional<Product> result = productService.getProductById(productId);
        List<BidDTO> listOfBidDTOs = null;
        if (result.isPresent()) {
            List<Bid> bids = bidRepository.findByProduct(result.get());
            listOfBidDTOs = convertToDTOList(bids);
        } else {
            throw new RuntimeException("Did not find product with id " + productId);
        }
        return listOfBidDTOs;
    }

    public BidDTO findById(Long id) {
        Optional<Bid> bid = bidRepository.findById(id);
        BidDTO bidDTO = null;
        if (bid.isPresent()) {
            bidDTO = convertToDTO(bid.get());
        }
        return bidDTO;
    }

    public BidDTO create(Bid bid) {
        Bid newBid = bidRepository.save(bid);
        return convertToDTO(newBid);
    }

    public void update(Long id, BidPriceDTO bidPrice) {
        Optional<Bid> oBid = bidRepository.findById(id);
        if (oBid.isPresent()) {
            Bid bid = oBid.get();
            bid.setPrice(bidPrice.getPrice());
            bidRepository.save(bid);
        }
    }

    public void deleteById(Long id) {
        bidRepository.deleteById(id);
    }


    private BidDTO convertToDTO(Bid bid) {
        BidDTO bidDTO = new BidDTO();
        bidDTO.setId(bid.getId());
        bidDTO.setPrice(bid.getPrice());
        if (bid.getProduct() != null) {
            bidDTO.setProductId(bid.getProduct().getId());
        }
        if (bid.getUser() != null) {
            bidDTO.setUserId(bid.getUser().getId());
        }
        return bidDTO;
    }

    private List<BidDTO> convertToDTOList(List<Bid> bids) {
        List<BidDTO> bidDTOList = new ArrayList<>();
        for (Bid bid : bids) {
            bidDTOList.add(convertToDTO(bid));
        }
        return bidDTOList;
    }
}
