package com.microtech.smartshop.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private double prix;

    @Column(nullable = false)
    private int stock;

    // Soft delete : Si true, le produit est considéré comme supprimé
    @Column(name = "is_deleted")
    private boolean deleted = false;
}