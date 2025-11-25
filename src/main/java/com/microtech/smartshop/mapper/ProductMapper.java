package com.microtech.smartshop.mapper;

import com.microtech.smartshop.dto.ProductDto;
import com.microtech.smartshop.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductDto toDto(Product product);
    Product toEntity(ProductDto productDto);

    @Mapping(target = "id", ignore = true)
//    @Mapping(target = "deleted", ignore = true)
    // mettre à jour produit existant
    void updateProductFromDto(ProductDto dto, @MappingTarget Product entity);
}