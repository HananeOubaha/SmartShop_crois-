package com.microtech.smartshop.controller;

import com.microtech.smartshop.dto.ClientDto;
import com.microtech.smartshop.entity.User;
import com.microtech.smartshop.service.ClientService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    // ADMIN ONLY: Lister tous les clients
    @GetMapping
    public ResponseEntity<Page<ClientDto>> getAll(Pageable pageable) {
        return ResponseEntity.ok(clientService.getAllClients(pageable));
    }

    // ADMIN: Créer un client
    @PostMapping
    public ResponseEntity<ClientDto> create(@RequestBody @Valid ClientDto dto) {
        return ResponseEntity.ok(clientService.createClient(dto));
    }

    // CLIENT: Voir mon profil (Selon la session)
    @GetMapping("/me")
    public ResponseEntity<ClientDto> getMyProfile(HttpSession session) {
        User user = (User) session.getAttribute("user");

        if (user == null) {
            return ResponseEntity.status(401).build(); // Non connecté
        }

        return clientService.getClientByUsername(user.getUsername())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}