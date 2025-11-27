package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.OrderItemRequest;
import com.microtech.smartshop.dto.OrderRequest;
import com.microtech.smartshop.entity.*;
import com.microtech.smartshop.enums.CustomerTier;
import com.microtech.smartshop.enums.OrderStatus;
import com.microtech.smartshop.repository.OrderRepository;
import com.microtech.smartshop.repository.ProductRepository;
import com.microtech.smartshop.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ClientRepository clientRepository;

    @Transactional // Mohima: bach ila w93 mochkil f wst l-hssab, koulchi y-t-annula (Rollback)
    public Order createOrder(Long clientId, OrderRequest request) {

        // 1. Récupérer le Client
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client introuvable"));

        // 2. Initialiser la commande vide
        Order order = Order.builder()
                .client(client)
                .createdAt(LocalDateTime.now())
                .status(OrderStatus.PENDING) // Dima katbda PENDING
                .promoCode(request.getPromoCode())
                .items(new ArrayList<>())
                .payments(new ArrayList<>())
                .build();

        double subTotal = 0.0;

        // 3. Boucle sur les produits demandés (OrderItemRequest)
        for (OrderItemRequest itemRequest : request.getItems()) {

            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("Produit ID " + itemRequest.getProductId() + " introuvable"));

            // CHECK STOCK (Règle Métier Critique)
            if (product.getStock() < itemRequest.getQuantity()) {
                throw new RuntimeException("Stock insuffisant pour le produit : " + product.getNom());
            }

            // Créer la ligne de commande (Snapshot du prix actuel)
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrix()) // On fige le prix
                    .order(order)
                    .build();

            // Ajouter à la liste et au sous-total
            order.getItems().add(orderItem);
            subTotal += orderItem.getTotalLine();
        }

        // 4. Calculs Financiers (L-Hssab)
        order.setSubTotal(subTotal);

        // Calcul Remise (Fidélité)
        double discountRate = getDiscountRate(client.getTier(), subTotal);
        double discountAmount = subTotal * discountRate;

        // Bonus: Code Promo (Si tu veux l'ajouter plus tard, c'est ici)
        // if ("PROMO-START".equals(request.getPromoCode())) { discountAmount += ... }

        order.setDiscountAmount(discountAmount);

        // Net à payer HT
        double netAmount = subTotal - discountAmount;

        // TVA (20% sur le net)
        double taxAmount = netAmount * 0.20;
        order.setTaxAmount(taxAmount);

        // Total TTC
        double totalAmount = netAmount + taxAmount;
        order.setTotalAmount(totalAmount);

        // Au début, le montant restant = Total TTC (car 0 payé)
        order.setRemainingAmount(totalAmount);

        // 5. Sauvegarder
        return orderRepository.save(order);
    }

    // Méthode Helper : Logique de la remise selon le Niveau (Tier)
    private double getDiscountRate(CustomerTier tier, double amount) {
        switch (tier) {
            case SILVER:
                return (amount >= 500) ? 0.05 : 0.0; // 5%
            case GOLD:
                return (amount >= 800) ? 0.10 : 0.0; // 10%
            case PLATINUM:
                return (amount >= 1200) ? 0.15 : 0.0; // 15%
            default:
                return 0.0; // BASIC = 0%
        }
    }
}