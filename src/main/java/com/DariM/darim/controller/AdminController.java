// package com.DariM.darim.controller;

// import com.DariM.darim.model.Chambre;
// import com.DariM.darim.service.AdminService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.List;

// @RestController
// @RequestMapping("/api/admin")
// public class AdminController {

//     @Autowired
//     private AdminService adminService;

//     @GetMapping("/annonces")
//     public ResponseEntity<List<Chambre>> getToutesLesAnnonces() {
//         return ResponseEntity.ok(adminService.getToutesLesAnnonces());
//     }

//     @PutMapping("/valider/{id}")
//     public ResponseEntity<String> validerAnnonce(@PathVariable Long id) {
//         adminService.validerAnnonce(id);
//         return ResponseEntity.ok("Annonce validée");
//     }

//     @DeleteMapping("/supprimer/{id}")
//     public ResponseEntity<String> supprimerAnnonce(@PathVariable Long id) {
//         adminService.supprimerAnnonce(id);
//         return ResponseEntity.ok("Annonce supprimée");
//     }
// }

