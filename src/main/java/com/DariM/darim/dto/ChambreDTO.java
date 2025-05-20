package com.DariM.darim.dto;

import lombok.Data;

@Data
public class ChambreDTO {
    private Long id;
    private String nom;
    private String description;
    private String adresse;
    private double prix;
    private String wilaya;
    private String moughataa;
    private boolean estAlloue;
    private int nombreDeReservations;

    // Ajout des getters manquants
    public String getTitre() {
        return this.nom; // Si 'titre' correspond à 'nom' dans votre modèle
    }

    public String getEmplacement() {
        return this.adresse; // Si 'emplacement' correspond à 'adresse' dans votre modèle
    }

    public ChambreDTO(Long id, String nom, String description, String adresse, double prix, String wilaya, String moughataa, boolean estAlloue, int nombreDeReservations) {
        this.id = id;
        this.nom = nom;
        this.description = description;
        this.adresse = adresse;
        this.prix = prix;
        this.wilaya = wilaya;
        this.moughataa = moughataa;
        this.estAlloue = estAlloue;
        this.nombreDeReservations = nombreDeReservations;
    }

    public String getDescription() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public double getPrix() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getWilaya() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    public String getMoughataa() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}