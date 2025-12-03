package com.example.auction.controller;

import com.example.auction.dto.RegisterDto;
import com.example.auction.model.User;
import com.example.auction.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;

@Controller
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) { this.userService = userService; }

    @GetMapping({"/", "/index"})
    public String index() { return "index"; }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("register", new RegisterDto());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("register") @Valid RegisterDto registerDto,
                               BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "register";
        }
        if (userService.findByEmail(registerDto.getEmail()) != null) {
            model.addAttribute("error", "Email already registered");
            return "register";
        }
        User user = new User();
        user.setEmail(registerDto.getEmail());
        user.setFullName(registerDto.getFullName());
        user.setPassword(registerDto.getPassword());
        userService.register(user);
        model.addAttribute("success", "Registration successful. Please login.");
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() { return "login"; }
}
