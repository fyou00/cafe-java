package model;

public class Drink extends MenuItem {
    private String type; // Kopi, Teh, Jus
    private String size; // Small, Medium, Large
    
    public Drink(String id, String name, double price, int stock, String type, String size) {
        super(id, name, price, stock);
        this.type = type;
        this.size = size;
    }
    
    public String getType() { return type; }
    public String getSize() { return size; }
    
    public void setType(String type) { this.type = type; }
    public void setSize(String size) { this.size = size; }
    
    // POLYMORPHISM - Override abstract method
    @Override
    public String getCategory() {
        return "Minuman - " + type;
    }
    
    @Override
    public String getDescription() {
        return getName() + " (" + size + ")";
    }
}