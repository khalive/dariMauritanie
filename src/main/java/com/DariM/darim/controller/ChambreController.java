// package com.DariM.darim.controller;

// import com.DariM.darim.dto.ChambreDTO;
// import com.DariM.darim.model.Chambre;
// import com.DariM.darim.model.User;
// import com.DariM.darim.service.ChambreService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.web.bind.annotation.*;

// import java.util.*;

// @RestController
// @RequestMapping("/api/chambres")
// public class ChambreController {

//     private final ChambreService chambreService;

//     public ChambreController(ChambreService chambreService) {
//         this.chambreService = chambreService;
//     }

//     // ➤ Accès public : liste des chambres disponibles
//     @GetMapping("")
//     public ResponseEntity<?> getAllChambresDisponibles() {
//         List<Chambre> chambres = chambreService.getChambresDisponibles();
//         return ResponseEntity.ok(Map.of("message", "Chambres disponibles récupérées avec succès", "data", chambres));
//     }

//     @GetMapping("/search")
//     public ResponseEntity<?> searchChambres(
//             @RequestParam(required = false) String wilaya,
//             @RequestParam(required = false) String moughataa,
//             @RequestParam(required = false) Double prixMax) {
//         List<Chambre> chambres = chambreService.searchChambres(wilaya, moughataa, prixMax);
//         return ResponseEntity.ok(Map.of("message", "Résultat de la recherche", "data", chambres));
//     }

//     @GetMapping("/{id}")
//     public ResponseEntity<?> getChambreById(@PathVariable Long id) {
//         Chambre chambre = chambreService.getChambreById(id);
//         if (chambre != null) {
//             return ResponseEntity.ok(Map.of("message", "Chambre trouvée", "data", chambre));
//         } else {
//             return ResponseEntity.status(404).body(Map.of("error", "Chambre non trouvée avec l'id: " + id));
//         }
//     }

//     // ➤ Accès utilisateur connecté
//     @PostMapping("")
//     public ResponseEntity<?> createChambre(@RequestBody ChambreDTO chambreDTO, @AuthenticationPrincipal User user) {
//         Chambre created = chambreService.createChambre(chambreDTO, user);
//         return ResponseEntity.ok(Map.of("message", "Chambre créée avec succès", "data", created));
//     }

//     @GetMapping("/user")
//     public ResponseEntity<?> getUserChambres(@AuthenticationPrincipal User user) {
//         List<Chambre> chambres = chambreService.getChambresByProprietaire(user);
//         return ResponseEntity.ok(Map.of("message", "Chambres de l'utilisateur récupérées", "data", chambres));
//     }

//     @PutMapping("/{id}")
//     public ResponseEntity<?> updateChambre(@PathVariable Long id, @RequestBody ChambreDTO chambreDTO, @AuthenticationPrincipal User user) {
//         Chambre updated = chambreService.updateChambre(id, chambreDTO, user);
//         if (updated != null) {
//             return ResponseEntity.ok(Map.of("message", "Chambre mise à jour avec succès", "data", updated));
//         } else {
//             return ResponseEntity.status(404).body(Map.of("error", "Échec de la mise à jour. Chambre non trouvée ou accès refusé."));
//         }
//     }

//     @DeleteMapping("/{id}")
//     public ResponseEntity<?> deleteChambre(@PathVariable Long id, @AuthenticationPrincipal User user) {
//         boolean deleted = chambreService.deleteChambre(id, user);
//         if (deleted) {
//             return ResponseEntity.ok(Map.of("message", "Chambre supprimée avec succès"));
//         } else {
//             return ResponseEntity.status(404).body(Map.of("error", "Échec de la suppression. Chambre introuvable ou accès refusé."));
//         }
//     }

//     // ➤ Admin : chambres en attente
//     @GetMapping("/admin/attente")
//     public ResponseEntity<?> getChambresEnAttente() {
//         List<Chambre> chambres = chambreService.getChambresEnAttente();
//         return ResponseEntity.ok(Map.of("message", "Chambres en attente récupérées", "data", chambres));
//     }

//     @PutMapping("/admin/{id}/valider")
//     public ResponseEntity<?> validerChambre(@PathVariable Long id) {
//         Chambre chambre = chambreService.validerChambre(id);
//         if (chambre != null) {
//             return ResponseEntity.ok(Map.of("message", "Chambre validée avec succès", "data", chambre));
//         } else {
//             return ResponseEntity.status(404).body(Map.of("error", "Chambre introuvable pour validation"));
//         }
//     }

//     @DeleteMapping("/admin/{id}")
//     public ResponseEntity<?> deleteChambreAdmin(@PathVariable Long id) {
//         chambreService.deleteChambreAdmin(id);
//         return ResponseEntity.ok(Map.of("message", "Chambre supprimée par admin"));
//     }

//     // ➤ Réservation
//     @PostMapping("/{id}/reserver")
//     public ResponseEntity<?> reserverChambre(@PathVariable Long id) {
//         Chambre chambre = chambreService.reserverChambre(id);
//         if (chambre != null) {
//             return ResponseEntity.ok(Map.of("message", "Chambre réservée avec succès", "data", chambre));
//         } else {
//             return ResponseEntity.badRequest().body(Map.of("error", "Impossible de réserver cette chambre"));
//         }
//     }

//     // ➤ Admin : libérer une chambre
//     @PutMapping("/admin/{id}/liberer")
//     public ResponseEntity<?> libererChambre(@PathVariable Long id) {
//         Chambre chambre = chambreService.libererChambre(id);
//         if (chambre != null) {
//             return ResponseEntity.ok(Map.of("message", "Chambre libérée avec succès", "data", chambre));
//         } else {
//             return ResponseEntity.badRequest().body(Map.of("error", "Échec de la libération de la chambre"));
//         }
//     }
// }

package com.DariM.darim.controller;

import com.DariM.darim.dto.ChambreDTO;
import com.DariM.darim.model.Chambre;
import com.DariM.darim.model.User;
import com.DariM.darim.service.ChambreService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chambres")
public class ChambreController {

    private final ChambreService chambreService;

    public ChambreController(ChambreService chambreService) {
        this.chambreService = chambreService;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            return (User) authentication.getPrincipal();
        }
        return null;
    }

    private boolean isAdmin() {
        User user = getCurrentUser();
        return user != null && user.getRole() == User.Role.ADMIN;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllChambresDisponibles() {
        List<Chambre> chambres = chambreService.getChambresDisponibles();
        return ResponseEntity.ok(Map.of("message", "Chambres disponibles récupérées avec succès", "data", chambres));
    }

    @GetMapping("/search")
    public ResponseEntity<?> searchChambres(
            @RequestParam(required = false) String wilaya,
            @RequestParam(required = false) String moughataa,
            @RequestParam(required = false) Double prixMax) {
        List<Chambre> chambres = chambreService.searchChambres(wilaya, moughataa, prixMax);
        return ResponseEntity.ok(Map.of("message", "Résultat de la recherche", "data", chambres));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChambreById(@PathVariable Long id) {
        Chambre chambre = chambreService.getChambreById(id);
        if (chambre != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre trouvée", "data", chambre));
        }
        return ResponseEntity.status(404).body(Map.of("error", "Chambre non trouvée"));
    }

    @PostMapping("")
    public ResponseEntity<?> createChambre(@RequestBody ChambreDTO chambreDTO) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Non authentifié"));
        }
        Chambre created = chambreService.createChambre(chambreDTO, user);
        return ResponseEntity.ok(Map.of("message", "Chambre créée avec succès", "data", created));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserChambres() {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Non authentifié"));
        }
        List<Chambre> chambres = chambreService.getChambresByProprietaire(user);
        return ResponseEntity.ok(Map.of("message", "Chambres de l'utilisateur récupérées", "data", chambres));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChambre(@PathVariable Long id, @RequestBody ChambreDTO chambreDTO) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Non authentifié"));
        }
        Chambre updated = chambreService.updateChambre(id, chambreDTO, user);
        if (updated != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre mise à jour", "data", updated));
        }
        return ResponseEntity.status(403).body(Map.of("error", "Accès refusé ou chambre introuvable"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChambre(@PathVariable Long id) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Non authentifié"));
        }
        boolean deleted = chambreService.deleteChambre(id, user);
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Chambre supprimée"));
        }
        return ResponseEntity.status(403).body(Map.of("error", "Accès refusé ou chambre introuvable"));
    }

    @GetMapping("/admin/attente")
    public ResponseEntity<?> getChambresEnAttente() {
        if (!isAdmin()) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé"));
        }
        List<Chambre> chambres = chambreService.getChambresEnAttente();
        return ResponseEntity.ok(Map.of("message", "Chambres en attente", "data", chambres));
    }

    @PutMapping("/admin/{id}/valider")
    public ResponseEntity<?> validerChambre(@PathVariable Long id) {
        if (!isAdmin()) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé"));
        }
        Chambre chambre = chambreService.validerChambre(id);
        if (chambre != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre validée", "data", chambre));
        }
        return ResponseEntity.status(404).body(Map.of("error", "Chambre introuvable"));
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteChambreAdmin(@PathVariable Long id) {
        if (!isAdmin()) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé"));
        }
        chambreService.deleteChambreAdmin(id);
        return ResponseEntity.ok(Map.of("message", "Chambre supprimée par admin"));
    }

    @PostMapping("/{id}/reserver")
    public ResponseEntity<?> reserverChambre(@PathVariable Long id) {
        User user = getCurrentUser();
        if (user == null) {
            return ResponseEntity.status(401).body(Map.of("error", "Non authentifié"));
        }
        Chambre chambre = chambreService.reserverChambre(id);
        if (chambre != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre réservée", "data", chambre));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Impossible de réserver"));
    }

    @PutMapping("/admin/{id}/liberer")
    public ResponseEntity<?> libererChambre(@PathVariable Long id) {
        if (!isAdmin()) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé"));
        }
        Chambre chambre = chambreService.libererChambre(id);
        if (chambre != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre libérée", "data", chambre));
        }
        return ResponseEntity.badRequest().body(Map.of("error", "Échec de libération"));
    }
}