package controller;

import model.*;
import java.util.ArrayList;
import java.util.List;

public class OrderController {
    private List<MenuItem> menuItems;
    private Order currentOrder;
    private List<Order> orderHistory;
    private int orderCounter;
    
    public OrderController() {
        menuItems = new ArrayList<>();
        orderHistory = new ArrayList<>();
        orderCounter = 1;
        initializeMenuItems();
        createNewOrder();
    }
    
    private void initializeMenuItems() {
        // POLYMORPHISM - List MenuItem bisa menampung Food dan Drink
        menuItems.add(new Food("F001", "Nasi Goreng", 15000, 50, "Pedas"));
        menuItems.add(new Food("F002", "Mie Ayam", 12000, 50, "Gurih"));
        menuItems.add(new Food("F003", "Ayam Geprek", 18000, 30, "Pedas"));
        menuItems.add(new Food("F004", "Sate Ayam", 20000, 25, "Manis"));
        menuItems.add(new Food("F005", "Bakso", 13000, 40, "Gurih"));
        
        menuItems.add(new Drink("D001", "Es Kopi Susu", 12000, 100, "Kopi", "Medium"));
        menuItems.add(new Drink("D002", "Kopi Hitam", 8000, 100, "Kopi", "Small"));
        menuItems.add(new Drink("D003", "Cappuccino", 15000, 80, "Kopi", "Large"));
        menuItems.add(new Drink("D004", "Es Teh Manis", 5000, 150, "Teh", "Medium"));
        menuItems.add(new Drink("D005", "Teh Tarik", 10000, 100, "Teh", "Medium"));
        menuItems.add(new Drink("D006", "Jus Jeruk", 12000, 60, "Jus", "Large"));
        menuItems.add(new Drink("D007", "Jus Alpukat", 15000, 50, "Jus", "Large"));
    }
    
    public void createNewOrder() {
        String orderId = "ORD" + String.format("%04d", orderCounter++);
        currentOrder = new Order(orderId);
    }
    
    public void addToOrder(MenuItem item, int quantity) {
        if (quantity > item.getStock()) {
            throw new IllegalArgumentException("Stok tidak cukup!");
        }
        
        OrderItem orderItem = new OrderItem(item, quantity);
        currentOrder.addItem(orderItem);
        
        // Kurangi stok
        item.setStock(item.getStock() - quantity);
    }
    
    public void removeFromOrder(int index) {
        if (index >= 0 && index < currentOrder.getItems().size()) {
            OrderItem item = currentOrder.getItems().get(index);
            // Kembalikan stok
            item.getMenuItem().setStock(item.getMenuItem().getStock() + item.getQuantity());
            currentOrder.removeItem(index);
        }
    }
    
    public void completeOrder() {
        if (currentOrder.getItems().isEmpty()) {
            throw new IllegalStateException("Order kosong!");
        }
        
        currentOrder.setStatus("Completed");
        orderHistory.add(currentOrder);
        createNewOrder();
    }
    
    public List<MenuItem> getMenuItems() { return menuItems; }
    public Order getCurrentOrder() { return currentOrder; }
    public List<Order> getOrderHistory() { return orderHistory; }
    
    public MenuItem findMenuById(String id) {
        for (MenuItem item : menuItems) {
            if (item.getId().equals(id)) {
                return item;
            }
        }
        return null;
    }
}