package com.microtech.smartshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderItemRequest {
    @NotNull(message = "ID produit obligatoire")
    private Long productId;

    @Min(value = 1, message = "Quantité minimale est 1")
    private int quantity;
}