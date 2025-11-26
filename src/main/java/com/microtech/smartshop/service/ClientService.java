package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.ClientDto;
import com.microtech.smartshop.entity.Client;
import com.microtech.smartshop.enums.CustomerTier;
import com.microtech.smartshop.enums.UserRole;
import com.microtech.smartshop.mapper.ClientMapper;
import com.microtech.smartshop.repository.ClientRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ClientMapper clientMapper;

    public Page<ClientDto> getAllClients(Pageable pageable) {
        return clientRepository.findAll(pageable).map(clientMapper::toDto);
    }

    public ClientDto createClient(ClientDto dto) {
        if (clientRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Username déjà utilisé");
        }

        Client client = clientMapper.toEntity(dto);

        // Valeurs par défaut obligatoires
        client.setRole(UserRole.CLIENT);
        client.setTier(CustomerTier.BASIC);
        client.setTotalOrders(0);
        client.setTotalSpent(0.0);

        // Password (en clair selon le brief, mais tu peux le hasher ici si tu veux)
        client.setPassword(dto.getPassword());

        return clientMapper.toDto(clientRepository.save(client));
    }

    // Pour qu'un client consulte son propre profil
    public Optional<ClientDto> getClientByUsername(String username) {
        return clientRepository.findByUsername(username)
                .map(clientMapper::toDto);
    }
}