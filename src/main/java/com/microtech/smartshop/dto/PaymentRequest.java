package com.microtech.smartshop.dto;

import com.microtech.smartshop.enums.PaymentStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PaymentRequest {
    @NotNull
    @Min(value = 1, message = "Le montant doit être positif")
    private Double amount;

    @NotBlank
    private String paymentMethod; // ESPECES, CHEQUE, VIREMENT

    private String reference; // Numéro chèque/virement (Facultatif pour Espèce)
}