package com.auctionapp.service;

import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.bid.BidPriceDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.model.user.User;
import com.auctionapp.repository.BidRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class BidService {

    private static final Logger logger = LoggerFactory.getLogger(BidService.class);
    private final BidRepository bidRepository;
    private final ProductService productService;

    private final UserService userService;

    private final AuctionService auctionService;

    public BidService(BidRepository bidRepository, ProductService productService, UserService userService, AuctionService auctionService) {
        this.bidRepository = bidRepository;
        this.productService = productService;
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @Transactional
    public List<BidDTO> findAll() {
        logger.info("Getting all bids");
        List<Bid> bids = bidRepository.findAll();
        List<BidDTO> listOfBidDTOs = convertToDTOList(bids);
        return listOfBidDTOs;
    }

    @Transactional
    public List<BidDTO> findByProductId(long productId) {
        logger.info("Getting bids by product ID: {}", productId);
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

    @Transactional
    public BidDTO findById(Long id) {
        logger.info("Getting bid by ID: {}", id);
        Optional<Bid> bid = bidRepository.findById(id);
        BidDTO bidDTO = null;
        if (bid.isPresent()) {
            bidDTO = convertToDTO(bid.get());
        }
        return bidDTO;
    }

    @Transactional
    public Bid create(BidDTO bidDTO) {
        logger.info("Creating bid");
        var oUser = userService.getUserById(bidDTO.getUserId());
        User user = oUser.orElse(null);
        var oProduct = productService.getProductById(bidDTO.getProductId());
        Product product = oProduct.orElse(null);
        var auctionDTO = auctionService.getAuctionById(bidDTO.getAuctionId());
        Bid newBid = new Bid(bidDTO.getPrice(), user, product, auctionDTO);
        return bidRepository.save(newBid);
    }

    @Transactional
    public void update(Long id, BidPriceDTO bidPrice) {
        logger.info("Updating bid with ID: {}", id);
        Optional<Bid> oBid = bidRepository.findById(id);
        if (oBid.isPresent()) {
            Bid bid = oBid.get();
            bid.setPrice(bidPrice.getPrice());
            bidRepository.save(bid);
            logger.info("Bid with ID {} updated successfully", id);
        }
    }

    @Transactional
    public void deleteById(Long id) {
        logger.info("Deleting bid with ID: {}", id);
        bidRepository.deleteById(id);
        logger.info("Bid with ID {} deleted successfully", id);
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
