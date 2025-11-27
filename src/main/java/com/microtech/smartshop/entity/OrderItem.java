package com.microtech.smartshop.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_items")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    @JsonIgnore
    private Order order;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    private int quantity;

    private double unitPrice; // Snapshot du prix au moment de l'achat

    // Champ calculé simple (transient signifie qu'il n'est pas stocké en base, juste calculé Java)
    public double getTotalLine() {
        return unitPrice * quantity;
    }
}