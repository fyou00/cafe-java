package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Order {
    private String orderId;
    private List<OrderItem> items;
    private double totalPrice;
    private LocalDateTime orderDate;
    private String status; // Pending, Completed
    
    public Order(String orderId) {
        this.orderId = orderId;
        this.items = new ArrayList<>();
        this.orderDate = LocalDateTime.now();
        this.status = "Pending";
    }
    
    public void addItem(OrderItem item) {
        items.add(item);
        calculateTotal();
    }
    
    public void removeItem(int index) {
        if (index >= 0 && index < items.size()) {
            items.remove(index);
            calculateTotal();
        }
    }
    
    public void calculateTotal() {
        totalPrice = 0;
        for (OrderItem item : items) {
            totalPrice += item.getSubtotal();
        }
    }
    
    public String getOrderId() { return orderId; }
    public List<OrderItem> getItems() { return items; }
    public double getTotalPrice() { return totalPrice; }
    public LocalDateTime getOrderDate() { return orderDate; }
    public String getStatus() { return status; }
    
    public void setStatus(String status) { this.status = status; }
    
    public String getFormattedDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return orderDate.format(formatter);
    }
}