package com.microtech.smartshop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.microtech.smartshop.enums.CustomerTier;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ClientDto {

    private Long id;

    @NotBlank(message = "Username obligatoire")
    private String username;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    @NotBlank(message = "Nom obligatoire")
    private String nom;

    @Email(message = "Format email invalide")
    @NotBlank(message = "Email obligatoire")
    private String email;

    private String telephone;

    // Champs lecture seule (gérés par le système)
    private CustomerTier tier;
    private int totalOrders;
    private double totalSpent;
}