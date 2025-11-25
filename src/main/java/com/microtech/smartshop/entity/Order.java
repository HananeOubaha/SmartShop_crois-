package com.microtech.smartshop.entity;

import com.microtech.smartshop.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private Client client;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    // Liste des paiements
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<Payment> payments = new ArrayList<>();

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // --- Côté Finance (Calculés) ---
    private double subTotal;      // total sans remise ni tva
    private double discountAmount; // remise
    private double taxAmount;     // TVA
    private double totalAmount;   // TTC ( total apres remise et tva )
    private double remainingAmount; //  (Total - Payé)

    private String promoCode;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    // Méthode utilitaire pour lier les items à la commande (Helper)
    public void addItem(OrderItem item) {
        items.add(item);
        item.setOrder(this);
    }
}