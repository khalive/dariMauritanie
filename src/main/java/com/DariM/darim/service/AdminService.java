// package com.DariM.darim.service;

// import com.DariM.darim.model.Chambre;
// import com.DariM.darim.repository.ChambreRepository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class AdminService {

//     private final ChambreRepository chambreRepository;

//     @Autowired
//     public AdminService(ChambreRepository chambreRepository) {
//         this.chambreRepository = chambreRepository;
//     }

//     public List<Chambre> getToutesLesAnnonces() {
//         return chambreRepository.findAll();
//     }

//     public Chambre validerAnnonce(Long id) {
//         Chambre chambre = chambreRepository.findById(id).orElseThrow();
//         chambre.setStatut("DISPONIBLE");
//         return chambreRepository.save(chambre);
//     }

//     public void supprimerAnnonce(Long id) {
//         chambreRepository.deleteById(id);
//     }
// }
