package com.microtech.smartshop.controller;

import com.microtech.smartshop.dto.LoginRequest;
import com.microtech.smartshop.entity.User;
import com.microtech.smartshop.service.AuthService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest request, HttpSession session) {
        // Appel du service pour vérifier login/pass
        User user = authService.authenticate(request);

        // SI OK -> On stocke l'objet User complet dans la session
        session.setAttribute("user", user);

        // On prépare une réponse JSON propre
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login réussi");
        response.put("user_id", user.getId());
        response.put("role", user.getRole());
        response.put("session_id", session.getId()); // Pour info

        return ResponseEntity.ok(response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpSession session) {
        session.invalidate(); // Détruit la session serveur
        return ResponseEntity.ok("Déconnexion réussie");
    }

    // Test pour voir si je suis connecté
    @GetMapping("/me")
    public ResponseEntity<?> me(HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(401).body("Non connecté");
        }
        return ResponseEntity.ok(user);
    }
}