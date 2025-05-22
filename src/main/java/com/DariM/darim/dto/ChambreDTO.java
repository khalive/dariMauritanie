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

    public String getTitre() {
        return this.nom;
    }

    public String getEmplacement() {
        return this.adresse;
    }
}