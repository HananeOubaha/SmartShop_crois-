package com.microtech.smartshop.controller;

import com.microtech.smartshop.dto.OrderRequest;
import com.microtech.smartshop.entity.Order;
import com.microtech.smartshop.entity.User;
import com.microtech.smartshop.service.OrderService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<Order> createOrder(@RequestBody @Valid OrderRequest request, HttpSession session) {
        // 1. Récupérer l'utilisateur connecté
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).build(); // Pas connecté
        }

        // 2. Appeler le service
        Order newOrder = orderService.createOrder(user.getId(), request);

        return ResponseEntity.ok(newOrder);
    }
}