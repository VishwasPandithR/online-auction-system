# Online Auction System

 **Online Auction System** — A simple real-time auction application built with Java Spring Boot, Thymeleaf, and WebSocket.

 ## Features

 - Logging in and registering users (Spring Security)  
 The seller may list auctions with the following details: title, description, starting price, and end time.  
 A dynamic list of ongoing auctions  
 - Real-time bidding with live updates (WebSocket + STOMP + SockJS)  
 For transparency, each auction's bidding history  
 By default, it uses an easy-to-set-up H2 in-memory database; MySQL can be used instead.  

 ## 📦 Tech Stack

 Spring Boot, Java 17, Spring Data JPA, H2, and MySQL  
 Thymeleaf for front-end templates; Spring WebSocket + STOMP (SockJS + STOMP.js); Spring Security; Maven  

 ## 🛠️ Setup & Run

 ```bash # Clone https://github.com/YourUser/online-auction-system.git git clone online-auction-system

 # Build mvn clean package

 # Execute mvn spring-boot:run
