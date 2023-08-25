package com.auctionapp.service;

import com.auctionapp.model.auction.Auction;
import com.auctionapp.model.auction.AuctionDTO;
import com.auctionapp.model.bid.Bid;
import com.auctionapp.model.bid.BidDTO;
import com.auctionapp.model.product.Product;
import com.auctionapp.repository.AuctionRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuctionService {

    private final AuctionRepository auctionRepository;
    private ProductService productService;

    public AuctionService(AuctionRepository auctionRepository) {
        this.auctionRepository = auctionRepository;
    }

    public Auction createAuction(Auction auction) {
        return auctionRepository.save(auction);
    }

    public List<Auction> getAllAuctions() {
        return auctionRepository.findAll();
    }

    public AuctionDTO getAuctionByProductId(Long productId)
    {
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

    public AuctionDTO getAuctionById (Long id)
    {
        Optional<Auction> auction = auctionRepository.findById(id);
        AuctionDTO auctionDTO = null;
        if (auction.isPresent()) {
            auctionDTO = convertToDTO(auction.get());
        }
        return auctionDTO;
    }

    public void updateAuction(Long id, AuctionDTO auctionWithUpdates) {
        Optional<Auction> oAuction = auctionRepository.findById(id);
        if (oAuction.isPresent()) {
            Auction auction = oAuction.get();
            auction.setType(auctionWithUpdates.getType());
            auction.setStartTime(auctionWithUpdates.getStartTime());
            auction.setEndTime(auctionWithUpdates.getEndTime());
            auction.setStartingPrice(auctionWithUpdates.getStartingPrice());
            auctionRepository.save(auction);
        }
    }

    public void deleteAuction(Long id) {
        auctionRepository.deleteById(id);
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
