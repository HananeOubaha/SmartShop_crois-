package com.microtech.smartshop.entity;

import com.microtech.smartshop.enums.CustomerTier;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "clients")
@Data @NoArgsConstructor @AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Client extends User {

    private String nom;

    private String email;

    @Column(name = "telephone")
    private String telephone;

    @Enumerated(EnumType.STRING)
    private CustomerTier tier = CustomerTier.BASIC;

    // Champs pour le suivi automatique (Statistiques)
    private int totalOrders = 0;
    private double totalSpent = 0.0;
}