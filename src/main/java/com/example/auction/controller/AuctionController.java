package com.example.auction.controller;

import com.example.auction.model.AuctionItem;
import com.example.auction.model.User;
import com.example.auction.service.AuctionService;
import com.example.auction.service.BidService;
import com.example.auction.service.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/auctions")
public class AuctionController {

    private final AuctionService auctionService;
    private final UserService userService;
    private final BidService bidService;

    public AuctionController(AuctionService auctionService,
                             UserService userService,
                             BidService bidService) {
        this.auctionService = auctionService;
        this.userService = userService;
        this.bidService = bidService;
    }

    @GetMapping
    public String listAuctions(Model model) {
        List<AuctionItem> active = auctionService.findAllActive();
        model.addAttribute("auctions", active);
        return "auctions";
    }

    @GetMapping("/create")
    public String showCreateForm(Model model) {
        model.addAttribute("item", new AuctionItem());
        return "auction_create";
    }

    @PostMapping("/create")
    public String createAuction(@ModelAttribute AuctionItem item, Authentication authentication) {
        if (authentication != null) {
            String email = authentication.getName();
            User seller = userService.findByEmail(email);
            item.setSeller(seller);
        }
        auctionService.createAuction(item);
        return "redirect:/auctions";
    }

    @GetMapping("/{id}")
    public String auctionDetail(@PathVariable Long id, Model model) {
        AuctionItem item = auctionService.findById(id);
        if (item == null) {
            return "redirect:/auctions";
        }
        model.addAttribute("item", item);
        model.addAttribute("bids", bidService.getBidsForAuction(item));
        return "auction_detail";
    }
}
