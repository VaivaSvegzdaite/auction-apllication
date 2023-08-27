package com.auctionapp.service;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.repository.AuctionRepository;
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
public class AuctionService {

    private static final Logger logger = LoggerFactory.getLogger(AuctionService.class);

    private final AuctionRepository auctionRepository;
    private final ProductService productService;

    public AuctionService (AuctionRepository auctionRepository, ProductService productService)
    {
        this.auctionRepository = auctionRepository;
        this.productService = productService;
    }

    public Auction createAuction(Auction auction) {
        logger.info("Creating auction");
        return auctionRepository.save(auction);
    }

    @Transactional
    public List<Auction> getAllAuctions() {
        logger.info("Getting all auctions");
        return auctionRepository.findAll();
    }

    @Transactional
    public List<Auction> getAuctionsByUserId(Long userId) {
        logger.info("Getting auctions by user ID: {}", userId);
        List<Auction> allAuctions = auctionRepository.findAll();
        List<Auction> auctionsByUserId = new ArrayList<>();
        for (Auction auction : allAuctions) {
            if (auction.getUser().getId() == userId)
                auctionsByUserId.add(auction);
        }
        return auctionsByUserId;
    }

    @Transactional
    public Auction getAuctionByProductId(Long productId) {
        logger.info("Getting auction by product ID: {}", productId);
        Auction answer = null;
        Optional<Product> result = productService.getProductById(productId);
        if (result.isPresent()) {
            Optional<Auction> auction = Optional.ofNullable(auctionRepository.findByProduct(result.get()));
            if (auction.isPresent()) {
                answer = auction.get();
            }
        }
        return answer;
    }

    @Transactional
    public Auction getAuctionById(Long id) {
        logger.info("Getting auction by ID: {}", id);
        return auctionRepository.findById(id).orElse(null);
    }

    @Transactional
    public void updateAuction(Long id, AuctionDTO auctionWithUpdates) {
        logger.info("Updating auction with ID: {}", id);
        Optional<Auction> oAuction = auctionRepository.findById(id);
        if (oAuction.isPresent()) {
            Auction auction = oAuction.get();
            auction.setType(auctionWithUpdates.getType());
            auction.setStartTime(auctionWithUpdates.getStartTime());
            auction.setEndTime(auctionWithUpdates.getEndTime());
            auction.setStartingPrice(auctionWithUpdates.getStartingPrice());
            auctionRepository.save(auction);
            logger.info("Auction with ID {} updated successfully", id);
        }
    }

    @Transactional
    public void deleteAuction(Long id) {
        logger.info("Deleting auction with ID: {}", id);
        auctionRepository.deleteById(id);
        logger.info("Auction with ID {} deleted successfully", id);
    }

    private AuctionDTO convertToDTO(Auction auction) {
        AuctionDTO auctionDTO = new AuctionDTO();
        auctionDTO.setId(auction.getId());
        auctionDTO.setType(auction.getType());
        auctionDTO.setStartTime(auction.getStartTime());
        auctionDTO.setEndTime(auction.getEndTime());
        //not editing bids here
        auctionDTO.setStartingPrice(auction.getStartingPrice());
        if (auction.getUser() != null) {
            auctionDTO.setUserId(auction.getUser().getId());
        }
        if (auction.getProduct() != null) {
            auctionDTO.setProductId(auction.getProduct().getId());
        }
        return auctionDTO;
    }

    private List<AuctionDTO> convertToDTOList(List<Auction> auctions) {
        List<AuctionDTO> auctionDTOList = new ArrayList<>();
        for (Auction auction : auctions) {
            auctionDTOList.add(convertToDTO(auction));
        }
        return auctionDTOList;
    }
}
