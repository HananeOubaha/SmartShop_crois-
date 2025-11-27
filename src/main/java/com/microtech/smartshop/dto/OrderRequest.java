package com.microtech.smartshop.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import java.util.List;

@Data
public class OrderRequest {

    @NotEmpty(message = "La commande ne peut pas être vide")
    private List<OrderItemRequest> items;

    // Optionnel : Code promo (Ex: PROMO-2025)
    private String promoCode;
}