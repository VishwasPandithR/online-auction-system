package com.example.auction.controller;

import com.example.auction.model.AuctionItem;
import com.example.auction.model.User;
import com.example.auction.service.AuctionService;
import com.example.auction.service.BidService;
import com.example.auction.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/bids")
public class BidController {

    private final BidService bidService;
    private final UserService userService;
    private final AuctionService auctionService;

    public BidController(BidService bidService, UserService userService, AuctionService auctionService) {
        this.bidService = bidService;
        this.userService = userService;
        this.auctionService = auctionService;
    }

    @PostMapping("/place")
    public ResponseEntity<?> placeBid(@RequestParam Long auctionId, @RequestParam String amount, Authentication authentication) {
        Map<String, Object> resp = new HashMap<>();
        try {
            if (authentication == null || !authentication.isAuthenticated()) {
                resp.put("error", "You must be logged in to place bids.");
                return ResponseEntity.status(401).body(resp);
            }
            User bidder = userService.findByEmail(authentication.getName());
            if (bidder == null) {
                resp.put("error", "User not found.");
                return ResponseEntity.status(401).body(resp);
            }
            BigDecimal amountBd = new BigDecimal(amount);
            bidService.placeBid(auctionId, bidder, amountBd);
            AuctionItem updated = auctionService.findById(auctionId);
            resp.put("success", true);
            resp.put("newPrice", updated.getCurrentPrice());
            return ResponseEntity.ok(resp);
        } catch (IllegalArgumentException ex) {
            resp.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(resp);
        } catch (Exception ex) {
            resp.put("error", "Unexpected error: " + ex.getMessage());
            return ResponseEntity.status(500).body(resp);
        }
    }
}
