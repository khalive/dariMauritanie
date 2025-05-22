package com.DariM.darim.dto;

import lombok.Data;

@Data
public class ChambreDTO {
    private Long id;
    private String titre;
    private String description;
    private String emplacement;
    private double prix;
    private String wilaya;
    private String moughataa;
    private boolean estAlloue;
    private int nombreDeReservations;

    // Getters standard générés par @Data
    // Pas besoin de méthodes supplémentaires car les noms correspondent maintenant
}