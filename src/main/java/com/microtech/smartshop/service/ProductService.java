package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.ProductDto;
import com.microtech.smartshop.entity.Product;
import com.microtech.smartshop.mapper.ProductMapper;
import com.microtech.smartshop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductMapper productMapper;

    // read all products
    public Page<ProductDto> getAllProducts(Pageable pageable) {
        return productRepository.findByDeletedFalse(pageable)
                .map(productMapper::toDto);
    }

    // CREATE : On retourne direct le DTO, pas besoin d'Optional (l'objet est forcément créé)
    public ProductDto createProduct(ProductDto dto) {
        Product product = productMapper.toEntity(dto);
        product.setDeleted(false);
        return productMapper.toDto(productRepository.save(product));
    }

    // READ :
    public Optional<ProductDto> getProduct(Long id) {
        return productRepository.findByIdAndDeletedFalse(id)
                .map(productMapper::toDto);
    }

    // UPDATE :
    public Optional<ProductDto> updateProduct(Long id, ProductDto dto) {
        return productRepository.findByIdAndDeletedFalse(id)
                .map(existingProduct -> {
                    productMapper.updateProductFromDto(dto, existingProduct);
                    return productRepository.save(existingProduct);
                })
                .map(productMapper::toDto);
    }

    // DELETE :
    public boolean deleteProduct(Long id) {
        return productRepository.findById(id)
                .map(product -> {
                    product.setDeleted(true);
                    productRepository.save(product);
                    return true;
                }).orElse(false);
    }
}