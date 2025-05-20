package com.DariM.darim.controller;

import com.DariM.darim.dto.ChambreDTO;
import com.DariM.darim.model.Chambre;
import com.DariM.darim.model.User;
import com.DariM.darim.service.ChambreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    // Accès Public
    @GetMapping("")
    public List<Chambre> getAllChambresDisponibles() {
        return chambreService.getChambresDisponibles();
    }

    @GetMapping("/search")
    public List<Chambre> searchChambres(
            @RequestParam(required = false) String wilaya,
            @RequestParam(required = false) String moughataa,
            @RequestParam(required = false) Double prixMax) {
        return chambreService.searchChambres(wilaya, moughataa, prixMax);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Chambre> getChambreById(@PathVariable Long id) {
        Chambre chambre = chambreService.getChambreById(id);
        return chambre != null ? ResponseEntity.ok(chambre) : ResponseEntity.notFound().build();
    }

    // Accès Utilisateur Connecté
    @PostMapping("")
    public ResponseEntity<Chambre> createChambre(@RequestBody ChambreDTO chambreDTO, @AuthenticationPrincipal User user) {
        Chambre createdChambre = chambreService.createChambre(chambreDTO, user);
        return ResponseEntity.ok(createdChambre);
    }

    @GetMapping("/user")
    public List<Chambre> getUserChambres(@AuthenticationPrincipal User user) {
        return chambreService.getChambresByProprietaire(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Chambre> updateChambre(@PathVariable Long id, @RequestBody ChambreDTO chambreDTO, @AuthenticationPrincipal User user) {
        Chambre updatedChambre = chambreService.updateChambre(id, chambreDTO, user);
        return updatedChambre != null ? ResponseEntity.ok(updatedChambre) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChambre(@PathVariable Long id, @AuthenticationPrincipal User user) {
        boolean deleted = chambreService.deleteChambre(id, user);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }

    // Accès Admin
    @GetMapping("/admin/attente")
    public List<Chambre> getChambresEnAttente() {
        return chambreService.getChambresEnAttente();
    }

    @PutMapping("/admin/{id}/valider")
    public ResponseEntity<Chambre> validerChambre(@PathVariable Long id) {
        Chambre chambre = chambreService.validerChambre(id);
        return chambre != null ? ResponseEntity.ok(chambre) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<Void> deleteChambreAdmin(@PathVariable Long id) {
        chambreService.deleteChambreAdmin(id);
        return ResponseEntity.noContent().build();
    }

    // Réservations
    @PostMapping("/{id}/reserver")
    public ResponseEntity<Chambre> reserverChambre(@PathVariable Long id) {
        Chambre chambre = chambreService.reserverChambre(id);
        return chambre != null ? ResponseEntity.ok(chambre) : ResponseEntity.badRequest().build();
    }

    // Option admin pour libérer une chambre
    @PutMapping("/admin/{id}/liberer")
    public ResponseEntity<Chambre> libererChambre(@PathVariable Long id) {
        Chambre chambre = chambreService.libererChambre(id);
        return chambre != null ? ResponseEntity.ok(chambre) : ResponseEntity.badRequest().build();
    }
}
