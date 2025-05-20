package com.DariM.darim.service;

import com.DariM.darim.dto.ChambreDTO;
import com.DariM.darim.model.Chambre;
import com.DariM.darim.model.User;
import com.DariM.darim.repository.ChambreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChambreService {

    private final ChambreRepository chambreRepository;

    public ChambreService(ChambreRepository chambreRepository) {
        this.chambreRepository = chambreRepository;
    }

    public List<Chambre> getChambresDisponibles() {
        return chambreRepository.findByStatutOrderByDatePublicationDesc("DISPONIBLE");
    }

    public List<Chambre> searchChambres(String wilaya, String moughataa, Double prixMax) {
        return chambreRepository.findByWilayaAndMoughataaAndPrixLessThanEqual(wilaya, moughataa, prixMax);
    }

    public Chambre getChambreById(Long id) {
        return chambreRepository.findById(id).orElse(null);
    }

    public Chambre createChambre(ChambreDTO chambreDTO, User proprietaire) {
        Chambre chambre = new Chambre();
        chambre.setTitre(chambreDTO.getTitre());
        chambre.setDescription(chambreDTO.getDescription());
        chambre.setPrix(chambreDTO.getPrix());
        chambre.setEmplacement(chambreDTO.getEmplacement());
        chambre.setWilaya(chambreDTO.getWilaya());
        chambre.setMoughataa(chambreDTO.getMoughataa());
        chambre.setStatut("EN_ATTENTE");
        chambre.setNbReservations(0);
        chambre.setDatePublication(LocalDateTime.now());
        chambre.setProprietaire(proprietaire);
        return chambreRepository.save(chambre);
    }

    public List<Chambre> getChambresByProprietaire(User proprietaire) {
        return chambreRepository.findByProprietaire(proprietaire);
    }

    public Chambre updateChambre(Long id, ChambreDTO chambreDTO, User proprietaire) {
        Chambre chambre = chambreRepository.findById(id).orElse(null);
        if (chambre != null && chambre.getProprietaire().getId().equals(proprietaire.getId())) {
            chambre.setTitre(chambreDTO.getTitre());
            chambre.setDescription(chambreDTO.getDescription());
            chambre.setPrix(chambreDTO.getPrix());
            chambre.setEmplacement(chambreDTO.getEmplacement());
            chambre.setWilaya(chambreDTO.getWilaya());
            chambre.setMoughataa(chambreDTO.getMoughataa());
            return chambreRepository.save(chambre);
        }
        return null;
    }

    public boolean deleteChambre(Long id, User proprietaire) {
        Chambre chambre = chambreRepository.findById(id).orElse(null);
        if (chambre != null && chambre.getProprietaire().getId().equals(proprietaire.getId())) {
            chambreRepository.delete(chambre);
            return true;
        }
        return false;
    }

    public List<Chambre> getChambresEnAttente() {
        return chambreRepository.findByStatut("EN_ATTENTE");
    }

    public Chambre validerChambre(Long id) {
        Chambre chambre = chambreRepository.findById(id).orElse(null);
        if (chambre != null) {
            chambre.setStatut("DISPONIBLE");
            return chambreRepository.save(chambre);
        }
        return null;
    }

    public void deleteChambreAdmin(Long id) {
        chambreRepository.deleteById(id);
    }

    public Chambre reserverChambre(Long id) {
        Chambre chambre = chambreRepository.findById(id).orElse(null);
        if (chambre != null && "DISPONIBLE".equals(chambre.getStatut())) {
            chambre.setNbReservations(chambre.getNbReservations() + 1);
            chambre.setStatut("ALLOUEE");
            return chambreRepository.save(chambre);
        }
        return null;
    }

    public Chambre libererChambre(Long id) {
        Chambre chambre = chambreRepository.findById(id).orElse(null);
        if (chambre != null && "ALLOUEE".equals(chambre.getStatut())) {
            chambre.setStatut("DISPONIBLE");
            return chambreRepository.save(chambre);
        }
        return null;
    }
}
