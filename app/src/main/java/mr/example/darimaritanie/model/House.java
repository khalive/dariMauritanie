package mr.example.darimaritanie.model;

public class House {
    public String imageName; // nom de l'image dans drawable
    public String location;
    public String owner;
    public String price;
    public boolean forSale; // true = vente, false = location
    public String phone;

    public House(String imageName, String location, String owner, String price, boolean forSale, String phone) {
        this.imageName = imageName;
        this.location = location;
        this.owner = owner;
        this.price = price;
        this.forSale = forSale;
        this.phone = phone;
    }
}