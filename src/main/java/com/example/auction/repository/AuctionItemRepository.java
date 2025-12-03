package com.example.auction.repository;

import com.example.auction.model.AuctionItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Repository;

import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem, Long> {
    List<AuctionItem> findByEndTimeAfterOrderByEndTimeAsc(LocalDateTime now);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    AuctionItem findAuctionItemById(Long id);
}
