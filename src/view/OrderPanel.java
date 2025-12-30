package view;

import controller.OrderController;
import model.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderPanel extends JPanel {
    private OrderController controller;
    
    // Left Panel Components
    private JTextField searchField;
    private JComboBox<String> categoryComboBox;
    private JList<String> menuList;
    private DefaultListModel<String> listModel;
    
    // Right Panel Components (Konfirmasi)
    private JTextField menuField;
    private JTextField priceField;
    private JTextField qtyField;
    private JTextField totalField;
    private JButton addButton;
    
    // Bottom Panel (Keranjang)
    private JTable cartTable;
    private DefaultTableModel cartTableModel;
    private JButton removeButton;
    private JButton checkoutButton;
    
    public OrderPanel(OrderController controller) {
        this.controller = controller;
        initComponents();
        loadMenuData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel dengan 3 bagian
        JPanel topPanel = new JPanel(new GridLayout(1, 3, 10, 0));
        topPanel.add(createLeftPanel());
        topPanel.add(createConfirmPanel());
        topPanel.add(createCartInfoPanel());
        add(topPanel, BorderLayout.NORTH);
        
        // Bottom Panel (Keranjang)
        add(createCartPanel(), BorderLayout.CENTER);
    }
    
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        
        // Search & Filter
        JPanel searchPanel = new JPanel(new BorderLayout(5, 5));
        searchField = new JTextField();
        searchField.setBorder(BorderFactory.createTitledBorder("nama cust"));
        
        categoryComboBox = new JComboBox<>(new String[]{
            "Semua", "Makanan", "Minuman - Kopi", "Minuman - Teh", "Minuman - Jus"
        });
        categoryComboBox.setBorder(BorderFactory.createTitledBorder("kategori"));
        categoryComboBox.addActionListener(e -> filterMenu());
        
        searchPanel.add(searchField, BorderLayout.NORTH);
        searchPanel.add(categoryComboBox, BorderLayout.CENTER);
        panel.add(searchPanel, BorderLayout.NORTH);
        
        // Menu List
        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.setBorder(BorderFactory.createTitledBorder("menu list"));
        listModel = new DefaultListModel<>();
        menuList = new JList<>(listModel);
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onMenuSelected();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(menuList);
        scrollPane.setPreferredSize(new Dimension(200, 150));
        listPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(listPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createConfirmPanel() {
        JPanel panel = new JPanel(new GridLayout(5, 2, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Konfirmasi"));
        panel.setBackground(new Color(144, 238, 144));
        
        panel.add(new JLabel("menu"));
        menuField = new JTextField();
        menuField.setEditable(false);
        panel.add(menuField);
        
        panel.add(new JLabel("harga"));
        priceField = new JTextField();
        priceField.setEditable(false);
        panel.add(priceField);
        
        panel.add(new JLabel("qty"));
        qtyField = new JTextField("1");
        qtyField.addActionListener(e -> updateTotal());
        panel.add(qtyField);
        
        panel.add(new JLabel("total"));
        totalField = new JTextField();
        totalField.setEditable(false);
        panel.add(totalField);
        
        panel.add(new JLabel(""));
        addButton = new JButton("add to keranjang");
        addButton.addActionListener(e -> addToCart());
        panel.add(addButton);
        
        return panel;
    }
    
    private JPanel createCartInfoPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setPreferredSize(new Dimension(150, 0));
        // Bisa ditambahkan info tambahan di sini
        return panel;
    }
    
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Keranjang"));
        
        // Table
        String[] columns = {"ID", "Nama", "Qty", "Subtotal"};
        cartTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        cartTable = new JTable(cartTableModel);
        cartTable.setBackground(new Color(255, 200, 150));
        JScrollPane scrollPane = new JScrollPane(cartTable);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        removeButton = new JButton("Hapus Item");
        removeButton.addActionListener(e -> removeFromCart());
        
        checkoutButton = new JButton("tambah pesanan");
        checkoutButton.addActionListener(e -> checkout());
        
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private void loadMenuData() {
        listModel.clear();
        for (MenuItem item : controller.getMenuItems()) {
            listModel.addElement(item.toString());
        }
    }
    
    private void filterMenu() {
        String category = (String) categoryComboBox.getSelectedItem();
        listModel.clear();
        
        for (MenuItem item : controller.getMenuItems()) {
            if (category.equals("Semua") || item.getCategory().contains(category)) {
                listModel.addElement(item.toString());
            }
        }
    }
    
    private void onMenuSelected() {
        int index = menuList.getSelectedIndex();
        if (index == -1) return;
        
        MenuItem item = getSelectedMenuItem();
        if (item != null) {
            menuField.setText(item.getName());
            priceField.setText(String.format("%.0f", item.getPrice()));
            qtyField.setText("1");
            updateTotal();
        }
    }
    
    private void updateTotal() {
        try {
            double price = Double.parseDouble(priceField.getText());
            int qty = Integer.parseInt(qtyField.getText());
            totalField.setText(String.format("%.0f", price * qty));
        } catch (NumberFormatException e) {
            totalField.setText("0");
        }
    }
    
    private MenuItem getSelectedMenuItem() {
        int index = menuList.getSelectedIndex();
        if (index == -1) return null;
        
        String category = (String) categoryComboBox.getSelectedItem();
        int count = 0;
        
        for (MenuItem item : controller.getMenuItems()) {
            if (category.equals("Semua") || item.getCategory().contains(category)) {
                if (count == index) return item;
                count++;
            }
        }
        return null;
    }
    
    private void addToCart() {
        MenuItem item = getSelectedMenuItem();
        if (item == null) {
            JOptionPane.showMessageDialog(this, "Pilih menu terlebih dahulu!");
            return;
        }
        
        try {
            int qty = Integer.parseInt(qtyField.getText());
            if (qty <= 0) {
                JOptionPane.showMessageDialog(this, "Jumlah harus lebih dari 0!");
                return;
            }
            
            controller.addToOrder(item, qty);
            refreshCart();
            clearConfirmPanel();
            
        } catch (IllegalArgumentException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Masukkan jumlah yang valid!");
        }
    }
    
    private void removeFromCart() {
        int row = cartTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Pilih item yang akan dihapus!");
            return;
        }
        
        controller.removeFromOrder(row);
        refreshCart();
    }
    
    private void checkout() {
        try {
            controller.completeOrder();
            JOptionPane.showMessageDialog(this, "Pesanan berhasil!", "Sukses", JOptionPane.INFORMATION_MESSAGE);
            refreshCart();
            searchField.setText("");
            loadMenuData();
        } catch (IllegalStateException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void refreshCart() {
        cartTableModel.setRowCount(0);
        Order order = controller.getCurrentOrder();
        
        for (OrderItem item : order.getItems()) {
            Object[] row = {
                item.getMenuItem().getId(),
                item.getMenuItem().getName(),
                item.getQuantity(),
                String.format("Rp %.0f", item.getSubtotal())
            };
            cartTableModel.addRow(row);
        }
    }
    
    private void clearConfirmPanel() {
        menuField.setText("");
        priceField.setText("");
        qtyField.setText("1");
        totalField.setText("");
        menuList.clearSelection();
    }
}

