package com.example.auction.service;

import com.example.auction.model.AuctionItem;
import com.example.auction.repository.AuctionItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AuctionService {

    private final AuctionItemRepository auctionItemRepository;

    public AuctionService(AuctionItemRepository auctionItemRepository) {
        this.auctionItemRepository = auctionItemRepository;
    }

    public List<AuctionItem> findAllActive() {
        return auctionItemRepository.findByEndTimeAfterOrderByEndTimeAsc(LocalDateTime.now());
    }

    public AuctionItem findById(Long id) {
        return auctionItemRepository.findById(id).orElse(null);
    }

    @Transactional
    public AuctionItem createAuction(AuctionItem item) {
        if (item.getCurrentPrice() == null) {
            item.setCurrentPrice(item.getStartingPrice());
        }
        return auctionItemRepository.save(item);
    }

    @Transactional
    public AuctionItem findByIdForUpdate(Long id) {
        return auctionItemRepository.findAuctionItemById(id);
    }
}
