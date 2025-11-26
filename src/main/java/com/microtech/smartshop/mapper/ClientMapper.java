package com.microtech.smartshop.mapper;

import com.microtech.smartshop.dto.ClientDto;
import com.microtech.smartshop.entity.Client;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientDto toDto(Client client);

    Client toEntity(ClientDto dto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) // On ne modifie pas le mot de passe ici
    @Mapping(target = "tier", ignore = true)     // Le niveau ne change pas manuellement
    @Mapping(target = "totalOrders", ignore = true)
    @Mapping(target = "totalSpent", ignore = true)
    void updateClientFromDto(ClientDto dto, @MappingTarget Client entity);
}