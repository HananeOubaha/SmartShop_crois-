package com.microtech.smartshop.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {
    private Long id;

    @NotBlank(message = "Le nom est obligatoire")
    private String nom;

    @NotBlank(message = "La description est obligatoire")
    private String description;

    @NotNull
    @Min(value = 0, message = "Le prix doit être positif")
    private Double prix;

    @Min(value = 0, message = "Le stock ne peut pas être négatif")
    private int stock;
}