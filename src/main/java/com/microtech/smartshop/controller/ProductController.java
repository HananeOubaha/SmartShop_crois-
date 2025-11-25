package com.microtech.smartshop.controller;

import com.microtech.smartshop.dto.ProductDto;
import com.microtech.smartshop.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<Page<ProductDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(productService.getAllProducts(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> getOne(@PathVariable Long id) {
        // C'est ICI qu'on gère le "Not Found" proprement
        return productService.getProduct(id)
                .map(ResponseEntity::ok) // Si présent -> 200 OK avec le body
                .orElseThrow(() -> new EntityNotFoundException("Produit non trouvé avec l'ID : " + id)); // Si vide -> 404 (via ControllerAdvice)
    }

    @PostMapping
    public ResponseEntity<ProductDto> create(@RequestBody @Valid ProductDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> update(@PathVariable Long id, @RequestBody @Valid ProductDto dto) {
        return productService.updateProduct(id, dto)
                .map(ResponseEntity::ok)
                .orElseThrow(() -> new EntityNotFoundException("Impossible de mettre à jour : Produit introuvable"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        if (productService.deleteProduct(id)) {
            return ResponseEntity.ok("Produit supprimé avec succès (Soft Delete)");
        } else {
            throw new EntityNotFoundException("Impossible de supprimer : Produit introuvable");
        }
    }
}