package model;

public class Food extends MenuItem {
    private String taste; // Pedas, Manis, Gurih, dll
    
    public Food(String id, String name, double price, int stock, String taste) {
        super(id, name, price, stock);
        this.taste = taste;
    }
    
    public String getTaste() { return taste; }
    public void setTaste(String taste) { this.taste = taste; }
    
    // POLYMORPHISM - Override abstract method
    @Override
    public String getCategory() {
        return "Makanan";
    }
    
    @Override
    public String getDescription() {
        return getName() + " (" + taste + ")";
    }
}