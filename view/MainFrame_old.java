package view;

import controller.OrderController;
import javax.swing.*;
import java.awt.*;

public class MainFrame_old extends JFrame {
    private OrderController controller;
    private OrderPanel_old orderPanel;
    private JButton historyButton;
    
    public MainFrame_old() {
        controller = new OrderController();
        initComponents();
        setTitle("Sistem Pemesanan Cafe - Kasir");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 700);
        setLocationRelativeTo(null);
        
        // Set background color
        getContentPane().setBackground(new Color(245, 245, 245));
    }
    
    private void initComponents() {
        // Menu Bar dengan style
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(new Color(101, 67, 33)); // Coklat cafe
        menuBar.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        
        JMenu menu = new JMenu("☰ Menu");
        menu.setForeground(Color.WHITE);
        menu.setFont(new Font("Segoe UI", Font.BOLD, 14));
        
        JMenuItem historyMenuItem = new JMenuItem("📋 Lihat History");
        historyMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        historyMenuItem.addActionListener(e -> showHistory());
        
        JMenuItem exitMenuItem = new JMenuItem("🚪 Keluar");
        exitMenuItem.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        exitMenuItem.addActionListener(e -> System.exit(0));
        
        menu.add(historyMenuItem);
        menu.addSeparator();
        menu.add(exitMenuItem);
        menuBar.add(menu);
        setJMenuBar(menuBar);
        
        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(139, 90, 43)); // Coklat gelap
        headerPanel.setPreferredSize(new Dimension(0, 60));
        headerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        
        JLabel titleLabel = new JLabel("☕ CAFE ORDER SYSTEM");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        
        add(headerPanel, BorderLayout.NORTH);
        
        // Main Panel dengan padding
        JPanel mainContainer = new JPanel(new BorderLayout());
        mainContainer.setBackground(new Color(245, 245, 245));
        mainContainer.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        orderPanel = new OrderPanel_old(controller);
        mainContainer.add(orderPanel, BorderLayout.CENTER);
        add(mainContainer, BorderLayout.CENTER);
        
        // Bottom Panel dengan style
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        bottomPanel.setBackground(new Color(245, 245, 245));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        historyButton = new JButton("📋 Lihat History Pesanan");
        styleButton(historyButton, new Color(52, 152, 219), Color.WHITE); // Biru
        historyButton.addActionListener(e -> showHistory());
        
        bottomPanel.add(historyButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void styleButton(JButton button, Color bgColor, Color fgColor) {
        button.setBackground(bgColor);
        button.setForeground(fgColor);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 35));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.brighter());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
    }
    
    private void showHistory() {
        HistoryFrame_old historyFrame = new HistoryFrame_old(controller);
        historyFrame.setVisible(true);
    }
}