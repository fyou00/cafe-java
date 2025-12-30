package view;

import controller.OrderController;
import model.Order;
import model.OrderItem;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class HistoryFrame_old extends JFrame {
    private OrderController controller;
    private JTable historyTable;
    private DefaultTableModel tableModel;
    private JTextArea detailArea;
    
    private final Color PRIMARY_COLOR = new Color(139, 90, 43);
    private final Color ACCENT_COLOR = new Color(46, 204, 113);
    
    public HistoryFrame_old(OrderController controller) {
        this.controller = controller;
        initComponents();
        loadHistory();
        
        setTitle("📋 History Pesanan");
        setSize(900, 600);
        setLocationRelativeTo(null);
        getContentPane().setBackground(new Color(245, 245, 245));
    }
    
    private void initComponents() {
        setLayout(new BorderLayout(15, 15));
        
        // Header
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(PRIMARY_COLOR);
        headerPanel.setPreferredSize(new Dimension(0, 60));
        
        JLabel titleLabel = new JLabel("📋 HISTORY PESANAN");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Container
        JPanel mainPanel = new JPanel(new BorderLayout(15, 15));
        mainPanel.setBackground(new Color(245, 245, 245));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Top Panel - Table
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);
        topPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel tableLabel = new JLabel("📊 Daftar Pesanan");
        tableLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        tableLabel.setForeground(PRIMARY_COLOR);
        tableLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        topPanel.add(tableLabel, BorderLayout.NORTH);
        
        String[] columns = {"Order ID", "Tanggal", "Total Item", "Total Harga", "Status"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        historyTable = new JTable(tableModel);
        historyTable.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyTable.setRowHeight(30);
        historyTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        historyTable.getTableHeader().setBackground(PRIMARY_COLOR);
        historyTable.getTableHeader().setForeground(Color.WHITE);
        historyTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        historyTable.setSelectionBackground(new Color(222, 184, 135));
        historyTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                showOrderDetail();
            }
        });
        
        JScrollPane tableScroll = new JScrollPane(historyTable);
        tableScroll.setBorder(BorderFactory.createLineBorder(new Color(222, 184, 135), 2));
        topPanel.add(tableScroll, BorderLayout.CENTER);
        mainPanel.add(topPanel, BorderLayout.CENTER);
        
        // Bottom Panel - Detail
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ACCENT_COLOR, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel detailLabel = new JLabel("📄 Detail Pesanan");
        detailLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        detailLabel.setForeground(ACCENT_COLOR);
        bottomPanel.add(detailLabel, BorderLayout.NORTH);
        
        detailArea = new JTextArea(8, 50);
        detailArea.setEditable(false);
        detailArea.setFont(new Font("Consolas", Font.PLAIN, 13));
        detailArea.setBackground(new Color(255, 250, 240));
        JScrollPane detailScroll = new JScrollPane(detailArea);
        detailScroll.setBorder(BorderFactory.createLineBorder(new Color(222, 184, 135), 2));
        bottomPanel.add(detailScroll, BorderLayout.CENTER);
        
        JButton closeButton = new JButton("✖ Tutup");
        styleButton(closeButton, new Color(231, 76, 60), Color.WHITE);
        closeButton.addActionListener(e -> dispose());
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);
        buttonPanel.add(closeButton);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(120, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
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
    
    private void loadHistory() {
        tableModel.setRowCount(0);
        
        for (Order order : controller.getOrderHistory()) {
            Object[] row = {
                order.getOrderId(),
                order.getFormattedDate(),
                order.getItems().size(),
                String.format("Rp %.0f", order.getTotalPrice()),
                order.getStatus()
            };
            tableModel.addRow(row);
        }
        
        if (controller.getOrderHistory().isEmpty()) {
            detailArea.setText("\n\n          Belum ada history pesanan.\n          Silakan buat pesanan terlebih dahulu.");
        }
    }
    
    private void showOrderDetail() {
        int row = historyTable.getSelectedRow();
        if (row == -1) return;
        
        Order order = controller.getOrderHistory().get(row);
        
        StringBuilder detail = new StringBuilder();
        detail.append("╔════════════════════════════════════════════════════════╗\n");
        detail.append(String.format("║  ORDER ID: %-43s ║\n", order.getOrderId()));
        detail.append(String.format("║  TANGGAL:  %-43s ║\n", order.getFormattedDate()));
        detail.append("╠════════════════════════════════════════════════════════╣\n");
        detail.append("║                    ITEM PESANAN                        ║\n");
        detail.append("╠════════════════════════════════════════════════════════╣\n");
        
        for (OrderItem item : order.getItems()) {
            detail.append(String.format("║  %-38s x%-3d  ║\n", 
                item.getMenuItem().getName(), 
                item.getQuantity()));
            detail.append(String.format("║      @ Rp %-15.0f = Rp %-15.0f  ║\n", 
                item.getMenuItem().getPrice(), 
                item.getSubtotal()));
            detail.append("║                                                        ║\n");
        }
        
        detail.append("╠════════════════════════════════════════════════════════╣\n");
        detail.append(String.format("║  TOTAL PEMBAYARAN:               Rp %-15.0f  ║\n", 
            order.getTotalPrice()));
        detail.append("╚════════════════════════════════════════════════════════╝\n");
        
        detailArea.setText(detail.toString());
    }
}