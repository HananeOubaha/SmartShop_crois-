package com.microtech.smartshop.service;

import com.microtech.smartshop.dto.LoginRequest;
import com.microtech.smartshop.entity.User;
import com.microtech.smartshop.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    public User authenticate(LoginRequest request) {
        // 1. Chercher l'utilisateur
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new RuntimeException("Utilisateur introuvable"));

        // 2. Vérifier le mot de passe
        if (!user.getPassword().equals(request.getPassword())) {
            throw new RuntimeException("Mot de passe incorrect");
        }

        // 3. Retourner l'utilisateur s'il est authentifié
        return user;
    }
}