package com.example.auction.repository;

import com.example.auction.model.Bid;
import com.example.auction.model.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {
    List<Bid> findByAuctionItemOrderByPlacedAtDesc(AuctionItem auctionItem);
}
