package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.PaymentRequest;
import com.microtech.smartshop.entity.Order;
import com.microtech.smartshop.entity.Payment;
import com.microtech.smartshop.enums.PaymentStatus;
import com.microtech.smartshop.repository.OrderRepository;
import com.microtech.smartshop.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class PaymentService {

    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private PaymentRepository paymentRepository; // Créi l'interface Repository vide si mazal madrtiha

    @Transactional
    public Payment addPayment(Long orderId, PaymentRequest request) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("Commande introuvable"));

        if (order.getRemainingAmount() <= 0) {
            throw new RuntimeException("Cette commande est déjà totalement payée !");
        }

        if (request.getAmount() > order.getRemainingAmount()) {
            throw new RuntimeException("Montant trop élevé. Reste à payer : " + order.getRemainingAmount());
        }

        // Création du paiement
        Payment payment = Payment.builder()
                .order(order)
                .amount(request.getAmount())
                .paymentMethod(request.getPaymentMethod())
                .reference(request.getReference())
                .paymentDate(LocalDate.now())
                .status(PaymentStatus.ENCAISSE) // On simplifie à ENCAISSE pour le test, ou EN_ATTENTE selon méthode
                .build();

        // Mise à jour du reste à payer
        double newRemaining = order.getRemainingAmount() - request.getAmount();
        order.setRemainingAmount(newRemaining);

        // Sauvegarde
        paymentRepository.save(payment);
        orderRepository.save(order);

        return payment;
    }
}