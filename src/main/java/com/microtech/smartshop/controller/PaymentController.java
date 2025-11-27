package com.microtech.smartshop.controller;

import com.microtech.smartshop.dto.PaymentRequest;
import com.microtech.smartshop.entity.Payment;
import com.microtech.smartshop.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders") // On groupe sous /orders
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    // POST /api/orders/{id}/payments
    @PostMapping("/{id}/payments")
    public ResponseEntity<Payment> addPayment(@PathVariable Long id, @RequestBody @Valid PaymentRequest request) {
        return ResponseEntity.ok(paymentService.addPayment(id, request));
    }
}