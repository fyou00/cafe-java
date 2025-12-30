package model;

public abstract class MenuItem {
    private String id;
    private String name;
    private double price;
    private int stock;
    
    public MenuItem(String id, String name, double price, int stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
    }
    
    // ENCAPSULATION - Getter methods
    public String getId() { return id; }
    public String getName() { return name; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
    
    // ENCAPSULATION - Setter methods
    public void setStock(int stock) { this.stock = stock; }
    public void setPrice(double price) { this.price = price; }
    
    // ABSTRACTION - Abstract methods yang harus diimplementasi child
    public abstract String getCategory();
    public abstract String getDescription();
    
    @Override
    public String toString() {
        return name + " - Rp " + String.format("%.0f", price);
    }
}