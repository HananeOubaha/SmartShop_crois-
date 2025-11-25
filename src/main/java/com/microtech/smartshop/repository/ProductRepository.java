package com.microtech.smartshop.repository;

import com.microtech.smartshop.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {

    // Njib ghir li machi supprimés (Pour l'affichage public)
    Page<Product> findByDeletedFalse(Pageable pageable);

    // Njib produit hta ila kan deleted (Bach n-affichiw detail f l-historique commande)
    Optional<Product> findByIdAndDeletedFalse(Long id);

    // Recherche par mot clé (Bonus recherche)
    Page<Product> findByNomContainingIgnoreCaseAndDeletedFalse(String nom, Pageable pageable);
}