    package com.DariM.darim.model;

    import jakarta.persistence.*;
    import java.time.LocalDateTime;
    import java.util.List;

    @Entity
    @Table(name = "chambres")
    public class Chambre {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Column(nullable = false, length = 200)
        private String titre;

        @Column(length = 1000)
        private String description;

        @Column(nullable = false)
        private double prix;

        @Column(length = 255)
        private String emplacement;

        @Column(length = 100)
        private String wilaya;

        @Column(length = 100)
        private String moughataa;

        @Column(nullable = false)
        private String statut; // "DISPONIBLE" ou "ALLOUEE"

        @Column(nullable = false)
        private int nbReservations = 0;

        private LocalDateTime datePublication;

        @ManyToOne
        @JoinColumn(name = "proprietaire_id")
        private User proprietaire;

        // Getters et Setters
        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getTitre() {
            return titre;
        }

        public void setTitre(String titre) {
            this.titre = titre;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }

        public String getEmplacement() {
            return emplacement;
        }

        public void setEmplacement(String emplacement) {
            this.emplacement = emplacement;
        }

        public String getWilaya() {
            return wilaya;
        }

        public void setWilaya(String wilaya) {
            this.wilaya = wilaya;
        }

        public String getMoughataa() {
            return moughataa;
        }

        public void setMoughataa(String moughataa) {
            this.moughataa = moughataa;
        }

        public String getStatut() {
            return statut;
        }

        public void setStatut(String statut) {
            this.statut = statut;
        }

        public int getNbReservations() {
            return nbReservations;
        }

        public void setNbReservations(int nbReservations) {
            this.nbReservations = nbReservations;
        }

        public LocalDateTime getDatePublication() {
            return datePublication;
        }

        public void setDatePublication(LocalDateTime datePublication) {
            this.datePublication = datePublication;
        }

        public User getProprietaire() {
            return proprietaire;
        }

        public void setProprietaire(User proprietaire) {
            this.proprietaire = proprietaire;
        }
    }
