package com.microtech.smartshop.entity;

import com.microtech.smartshop.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "payments")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(nullable = false)
    private Double amount;

    @Column(nullable = false)
    private String paymentMethod; // ESPECES, CHEQUE, VIREMENT

    private String reference; // Numéro chèque ou virement

    private LocalDate paymentDate; // Date du paiement

    @Enumerated(EnumType.STRING)
    private PaymentStatus status; // ENCAISSE, EN_ATTENTE...
}