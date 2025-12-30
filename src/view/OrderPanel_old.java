package view;

import controller.OrderController;
//import model.MenuItem;
import model.Order;
import model.OrderItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class OrderPanel_old extends JPanel {
    private final OrderController controller;
    
    // Colors
    private final Color PRIMARY_COLOR = new Color(139, 90, 43); // Coklat cafe
    private final Color SECONDARY_COLOR = new Color(222, 184, 135); // Tan
    private final Color ACCENT_COLOR = new Color(46, 204, 113); // Hijau
    private final Color DANGER_COLOR = new Color(231, 76, 60); // Merah
    private final Color INFO_COLOR = new Color(52, 152, 219); // Biru
    
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
    private JLabel grandTotalLabel;
    
    public OrderPanel_old(OrderController controller) {
        this.controller = controller;
        initComponents();
        loadMenuData();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        setBackground(new Color(245, 245, 245));
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Top Panel dengan 2 bagian (Left + Right Confirm)
        JPanel topPanel = new JPanel(new GridLayout(1, 2, 15, 0));
        topPanel.setOpaque(false);
        topPanel.add(createLeftPanel());
        topPanel.add(createConfirmPanel());
        add(topPanel, BorderLayout.NORTH);
        
        // Bottom Panel (Keranjang)
        add(createCartPanel(), BorderLayout.CENTER);
    }
    
    private JPanel createLeftPanel() {
        JPanel panel = new JPanel(new BorderLayout(5, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Title
        JLabel titleLabel = new JLabel("📋 MENU");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(PRIMARY_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Search & Filter Panel
        JPanel filterPanel = new JPanel(new GridLayout(2, 1, 5, 10));
        filterPanel.setOpaque(false);
        
        // Search Field
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setOpaque(false);
        JLabel searchLabel = new JLabel("👤 Nama Customer:");
        searchLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchField = new JTextField();
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        searchField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        searchPanel.add(searchLabel, BorderLayout.NORTH);
        searchPanel.add(searchField, BorderLayout.CENTER);
        
        // Category ComboBox
        JPanel categoryPanel = new JPanel(new BorderLayout());
        categoryPanel.setOpaque(false);
        JLabel catLabel = new JLabel("🏷️ Kategori:");
        catLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        categoryComboBox = new JComboBox<>(new String[]{
            "Semua", "Makanan", "Minuman - Kopi", "Minuman - Teh", "Minuman - Jus"
        });
        categoryComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        categoryComboBox.addActionListener(e -> filterMenu());
        categoryPanel.add(catLabel, BorderLayout.NORTH);
        categoryPanel.add(categoryComboBox, BorderLayout.CENTER);
        
        filterPanel.add(searchPanel);
        filterPanel.add(categoryPanel);
        panel.add(filterPanel, BorderLayout.CENTER);
        
        // Menu List
        JPanel listPanel = new JPanel(new BorderLayout(5, 5));
        listPanel.setOpaque(false);
        JLabel listLabel = new JLabel("🍽️ Daftar Menu:");
        listLabel.setFont(new Font("Segoe UI", Font.BOLD, 12));
        listLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));
        
        listModel = new DefaultListModel<>();
        menuList = new JList<>(listModel);
        menuList.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        menuList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menuList.setSelectionBackground(SECONDARY_COLOR);
        menuList.setSelectionForeground(Color.BLACK);
        menuList.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        menuList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                onMenuSelected();
            }
        });
        
        JScrollPane scrollPane = new JScrollPane(menuList);
        scrollPane.setPreferredSize(new Dimension(300, 180));
        scrollPane.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 2));
        
        listPanel.add(listLabel, BorderLayout.NORTH);
        listPanel.add(scrollPane, BorderLayout.CENTER);
        panel.add(listPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createConfirmPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(new Color(255, 250, 240)); // Cream
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Title
        JLabel titleLabel = new JLabel("✅ KONFIRMASI PESANAN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(ACCENT_COLOR);
        panel.add(titleLabel, BorderLayout.NORTH);
        
        // Form
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 15));
        formPanel.setOpaque(false);
        
        // Menu
        formPanel.add(createLabel("🍴 Menu:"));
        menuField = createStyledTextField();
        menuField.setEditable(false);
        menuField.setBackground(new Color(240, 240, 240));
        formPanel.add(menuField);
        
        // Harga
        formPanel.add(createLabel("💰 Harga:"));
        priceField = createStyledTextField();
        priceField.setEditable(false);
        priceField.setBackground(new Color(240, 240, 240));
        formPanel.add(priceField);
        
        // Qty
        formPanel.add(createLabel("🔢 Jumlah:"));
        qtyField = createStyledTextField();
        qtyField.setText("1");
        qtyField.addActionListener(e -> updateTotal());
        formPanel.add(qtyField);
        
        // Total
        formPanel.add(createLabel("💵 Total:"));
        totalField = createStyledTextField();
        totalField.setEditable(false);
        totalField.setBackground(new Color(240, 240, 240));
        totalField.setFont(new Font("Segoe UI", Font.BOLD, 14));
        totalField.setForeground(ACCENT_COLOR);
        formPanel.add(totalField);
        
        panel.add(formPanel, BorderLayout.CENTER);
        
        // Add Button
        addButton = new JButton("➕ TAMBAH KE KERANJANG");
        styleButton(addButton, ACCENT_COLOR, Color.WHITE, 16);
        addButton.addActionListener(e -> addToCart());
        panel.add(addButton, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createCartPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(INFO_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        // Header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        
        JLabel titleLabel = new JLabel("🛒 KERANJANG BELANJA");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(INFO_COLOR);
        
        grandTotalLabel = new JLabel("Total: Rp 0");
        grandTotalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        grandTotalLabel.setForeground(ACCENT_COLOR);
        
        headerPanel.add(titleLabel, BorderLayout.WEST);
        headerPanel.add(grandTotalLabel, BorderLayout.EAST);
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"ID", "Nama Menu", "Qty", "Subtotal"};
        cartTableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        cartTable = new JTable(cartTableModel);
        cartTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        cartTable.setRowHeight(30);
        cartTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        cartTable.getTableHeader().setBackground(INFO_COLOR);
        cartTable.getTableHeader().setForeground(Color.WHITE);
        cartTable.setSelectionBackground(SECONDARY_COLOR);
        
        JScrollPane scrollPane = new JScrollPane(cartTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(SECONDARY_COLOR, 1));
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        buttonPanel.setOpaque(false);
        
        removeButton = new JButton("🗑️ Hapus Item");
        styleButton(removeButton, DANGER_COLOR, Color.WHITE, 13);
        removeButton.addActionListener(e -> removeFromCart());
        
        checkoutButton = new JButton("✅ SELESAI & BAYAR");
        styleButton(checkoutButton, ACCENT_COLOR, Color.WHITE, 14);
        checkoutButton.setPreferredSize(new Dimension(180, 40));
        checkoutButton.addActionListener(e -> checkout());
        
        buttonPanel.add(removeButton);
        buttonPanel.add(checkoutButton);
        panel.add(buttonPanel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(new Font("Segoe UI", Font.BOLD, 13));
        return label;
    }
    
    private JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        field.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(SECONDARY_COLOR, 2),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        return field;
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor, int fontSize) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, fontSize));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(150, 35));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    private void loadMenuData() {
        listModel.clear();
        for (model.MenuItem item : controller.getMenuItems()) {
            listModel.addElement(item.toString());
        }
    }
    
    private void filterMenu() {
        String category = (String) categoryComboBox.getSelectedItem();
        listModel.clear();
        
        for (model.MenuItem item : controller.getMenuItems()) {
            if (category.equals("Semua") || item.getCategory().contains(category)) {
                listModel.addElement(item.toString());
            }
        }
    }
    
    private void onMenuSelected() {
        int index = menuList.getSelectedIndex();
        if (index == -1) return;
        
        model.MenuItem item = getSelectedMenuItem();
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
    
    private model.MenuItem getSelectedMenuItem() {
        int index = menuList.getSelectedIndex();
        if (index == -1) return null;
        
        String category = (String) categoryComboBox.getSelectedItem();
        int count = 0;
        
        for (model.MenuItem item : controller.getMenuItems()) {
            if (category.equals("Semua") || item.getCategory().contains(category)) {
                if (count == index) return item;
                count++;
            }
        }
        return null;
    }
    
    private void addToCart() {
        model.MenuItem item = getSelectedMenuItem();
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
        double grandTotal = 0;
        
        for (OrderItem item : order.getItems()) {
            Object[] row = {
                item.getMenuItem().getId(),
                item.getMenuItem().getName(),
                item.getQuantity(),
                String.format("Rp %.0f", item.getSubtotal())
            };
            cartTableModel.addRow(row);
            grandTotal += item.getSubtotal();
        }
        
        // Update grand total label
        grandTotalLabel.setText(String.format("Total: Rp %.0f", grandTotal));
    }
    
    private void clearConfirmPanel() {
        menuField.setText("");
        priceField.setText("");
        qtyField.setText("1");
        totalField.setText("");
        menuList.clearSelection();
    }
}