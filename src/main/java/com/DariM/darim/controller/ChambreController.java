// package com.DariM.darim.controller;

// import com.DariM.darim.dto.ChambreDTO;
// import com.DariM.darim.model.Chambre;
// import com.DariM.darim.model.User;
// import com.DariM.darim.service.ChambreService;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.core.annotation.AuthenticationPrincipal;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;
// import java.util.Map;

// @RestController
// @RequestMapping("/api/chambres")
// public class ChambreController {

//     private final ChambreService chambreService;

//     public ChambreController(ChambreService chambreService) {
//         this.chambreService = chambreService;
//     }

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

//     @PostMapping("/{id}/reserver")
//     public ResponseEntity<?> reserverChambre(@PathVariable Long id) {
//         Chambre chambre = chambreService.reserverChambre(id);
//         if (chambre != null) {
//             return ResponseEntity.ok(Map.of("message", "Chambre réservée avec succès", "data", chambre));
//         } else {
//             return ResponseEntity.badRequest().body(Map.of("error", "Impossible de réserver cette chambre"));
//         }
//     }

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
import com.DariM.darim.repository.UserRepository;
import com.DariM.darim.service.ChambreService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/chambres")
public class ChambreController {

    private final ChambreService chambreService;
    private final UserRepository userRepository;

    public ChambreController(ChambreService chambreService, UserRepository userRepository) {
        this.chambreService = chambreService;
        this.userRepository = userRepository;
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
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Chambre non trouvée avec l'id: " + id));
        }
    }

    @PostMapping("")
    public ResponseEntity<?> createChambre(@RequestBody ChambreDTO chambreDTO, 
                                         @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur non trouvé"));
        }
        
        User user = userOptional.get();
        Chambre created = chambreService.createChambre(chambreDTO, user);
        return ResponseEntity.ok(Map.of("message", "Chambre créée avec succès", "data", created));
    }

    @GetMapping("/user")
    public ResponseEntity<?> getUserChambres(@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur non trouvé"));
        }
        
        List<Chambre> chambres = chambreService.getChambresByProprietaire(userOptional.get());
        return ResponseEntity.ok(Map.of("message", "Chambres de l'utilisateur récupérées", "data", chambres));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateChambre(@PathVariable Long id, 
                                         @RequestBody ChambreDTO chambreDTO,
                                         @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur non trouvé"));
        }
        
        User user = userOptional.get();
        Chambre updated = chambreService.updateChambre(id, chambreDTO, user);
        if (updated != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre mise à jour avec succès", "data", updated));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Échec de la mise à jour. Chambre non trouvée ou accès refusé."));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteChambre(@PathVariable Long id,
                                         @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur non trouvé"));
        }
        
        boolean deleted = chambreService.deleteChambre(id, userOptional.get());
        if (deleted) {
            return ResponseEntity.ok(Map.of("message", "Chambre supprimée avec succès"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Échec de la suppression. Chambre introuvable ou accès refusé."));
        }
    }

    @GetMapping("/admin/attente")
    public ResponseEntity<?> getChambresEnAttente(@RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty() || userOptional.get().getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé. Admin requis."));
        }
        
        List<Chambre> chambres = chambreService.getChambresEnAttente();
        return ResponseEntity.ok(Map.of("message", "Chambres en attente récupérées", "data", chambres));
    }

    @PutMapping("/admin/{id}/valider")
    public ResponseEntity<?> validerChambre(@PathVariable Long id,
                                          @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty() || userOptional.get().getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé. Admin requis."));
        }
        
        Chambre chambre = chambreService.validerChambre(id);
        if (chambre != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre validée avec succès", "data", chambre));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "Chambre introuvable pour validation"));
        }
    }

    @DeleteMapping("/admin/{id}")
    public ResponseEntity<?> deleteChambreAdmin(@PathVariable Long id,
                                              @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty() || userOptional.get().getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé. Admin requis."));
        }
        
        chambreService.deleteChambreAdmin(id);
        return ResponseEntity.ok(Map.of("message", "Chambre supprimée par admin"));
    }

    @PostMapping("/{id}/reserver")
    public ResponseEntity<?> reserverChambre(@PathVariable Long id,
                                           @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.badRequest().body(Map.of("error", "Utilisateur non trouvé"));
        }
        
        Chambre chambre = chambreService.reserverChambre(id);
        if (chambre != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre réservée avec succès", "data", chambre));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Impossible de réserver cette chambre"));
        }
    }

    @PutMapping("/admin/{id}/liberer")
    public ResponseEntity<?> libererChambre(@PathVariable Long id,
                                          @RequestParam Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty() || userOptional.get().getRole() != User.Role.ADMIN) {
            return ResponseEntity.status(403).body(Map.of("error", "Accès refusé. Admin requis."));
        }
        
        Chambre chambre = chambreService.libererChambre(id);
        if (chambre != null) {
            return ResponseEntity.ok(Map.of("message", "Chambre libérée avec succès", "data", chambre));
        } else {
            return ResponseEntity.badRequest().body(Map.of("error", "Échec de la libération de la chambre"));
        }
    }
}