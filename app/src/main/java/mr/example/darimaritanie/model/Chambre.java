package mr.example.darimaritanie.model;

public class Chambre {
    private Long id;
    private String titre;
    private String description;
    private double prix;
    private String emplacement;
    private String wilaya;
    private String moughataa;
    private String statut;
    private int nbReservations;
    private String imgUrl;

    // Constructor
    public Chambre(String titre, String description, double prix, String emplacement,
                   String wilaya, String moughataa, String statut,
                   int nbReservations, String imgUrl) {
        this.titre = titre;
        this.description = description;
        this.prix = prix;
        this.emplacement = emplacement;
        this.wilaya = wilaya;
        this.moughataa = moughataa;
        this.statut = statut;
        this.nbReservations = nbReservations;
        this.imgUrl = imgUrl;
    }

    // Getters and Setters
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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
}