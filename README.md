# 🏷️ Online Auction System  
A real-time online auction platform built with **Spring Boot**, **Thymeleaf**, **Spring Security**, **WebSockets**, and **H2**.  
Users can create auctions, bid in real time, and view live updates instantly.

---

## 🚀 Features

### 👤 User Features
- User Registration & Login  
- Profile-based access (auth required to bid / create auctions)  
- Secure password encryption (Spring Security)

### 🛒 Auction Features
- Create new auction items (title, description, start price, end time)  
- View all active auctions  
- Auction countdown timer  
- Real-time bid updates (WebSocket + STOMP)

### 💰 Bidding Features
- Only logged-in users can place bids  
- Validations ensure higher bid amounts  
- Real-time updates pushed to all connected clients  
- Transparent bidding history per item

---

## 🧰 Tech Stack

| Layer | Technology |
|-------|------------|
| Backend | Spring Boot (Web, Security, JPA) |
| Frontend | Thymeleaf, Bootstrap 5 |
| Database | H2 (Default) |
| Real-time | Spring WebSocket, STOMP, SockJS |
| Build Tool | Maven |
| Language | Java 17 |

---

## 📦 Project Structure

