package model;

public class OrderItem {
    private MenuItem menuItem;
    private int quantity;
    private double subtotal;
    
    public OrderItem(MenuItem menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
    
    public double calculateSubtotal() {
        return menuItem.getPrice() * quantity;
    }
    
    public MenuItem getMenuItem() { return menuItem; }
    public int getQuantity() { return quantity; }
    public double getSubtotal() { return subtotal; }
    
    public void setQuantity(int quantity) {
        this.quantity = quantity;
        this.subtotal = calculateSubtotal();
    }
}