package com.DariM.darim.repository;

import com.DariM.darim.model.Chambre;
import com.DariM.darim.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChambreRepository extends JpaRepository<Chambre, Long> {
    List<Chambre> findByStatutOrderByDatePublicationDesc(String statut);
    List<Chambre> findByProprietaire(User proprietaire);
    List<Chambre> findByWilayaAndMoughataaAndPrixLessThanEqual(String wilaya, String moughataa, double prixMax);
    List<Chambre> findByStatut(String statut);
}
