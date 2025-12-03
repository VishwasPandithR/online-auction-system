package com.example.auction.service;

import com.example.auction.model.AuctionItem;
import com.example.auction.model.Bid;
import com.example.auction.model.User;
import com.example.auction.repository.BidRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.util.List;

@Service
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionService auctionService;
    private final SimpMessagingTemplate messagingTemplate;

    public BidService(BidRepository bidRepository,
                      AuctionService auctionService,
                      SimpMessagingTemplate messagingTemplate) {
        this.bidRepository = bidRepository;
        this.auctionService = auctionService;
        this.messagingTemplate = messagingTemplate;
    }

    public List<Bid> getBidsForAuction(AuctionItem item) {
        return bidRepository.findByAuctionItemOrderByPlacedAtDesc(item);
    }

    @Transactional
    public Bid placeBid(Long auctionId, User bidder, BigDecimal amount) throws IllegalArgumentException {
        AuctionItem item = auctionService.findByIdForUpdate(auctionId);
        if (item == null) {
            throw new IllegalArgumentException("Auction item not found");
        }
        if (item.getEndTime() != null && item.getEndTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Auction has already ended");
        }
        BigDecimal current = item.getCurrentPrice() != null ? item.getCurrentPrice() : item.getStartingPrice();
        if (amount.compareTo(current) <= 0) {
            throw new IllegalArgumentException("Bid must be higher than current price (" + current + ")");
        }
        Bid bid = new Bid();
        bid.setAmount(amount);
        bid.setBidder(bidder);
        bid.setPlacedAt(LocalDateTime.now());
        bid.setAuctionItem(item);
        Bid saved = bidRepository.save(bid);
        item.setCurrentPrice(amount);
        messagingTemplate.convertAndSend("/topic/auction." + auctionId,
                new AuctionUpdateDTO(auctionId, amount, bidder.getFullName()));
        return saved;
    }

    public static class AuctionUpdateDTO {
        private Long auctionId;
        private java.math.BigDecimal newPrice;
        private String bidderName;

        public AuctionUpdateDTO(Long auctionId, java.math.BigDecimal newPrice, String bidderName) {
            this.auctionId = auctionId;
            this.newPrice = newPrice;
            this.bidderName = bidderName;
        }

        public Long getAuctionId() { return auctionId; }
        public java.math.BigDecimal getNewPrice() { return newPrice; }
        public String getBidderName() { return bidderName; }
    }
}
