import controller.OrderController;
import javax.swing.*;
import view.OrderPanel;

public class Main {
    public static void main(String[] args) {
        // Set Look and Feel
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Run application
        SwingUtilities.invokeLater(() -> {
            // Buat frame sementara
            JFrame frame = new JFrame("Cafe Order System - Preview");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Tambahkan OrderPanel langsung sebagai content
            OrderPanel orderPanel = new OrderPanel();
            frame.setContentPane(orderPanel);
            
            OrderController controller = new OrderController();
            orderPanel.setController(controller);
            
            // Atur ukuran & tampilkan
            frame.pack();
            if (frame.getPreferredSize().width < 600) {
                frame.setSize(800, 600);
            }
            frame.setLocationRelativeTo(null); // tengah layar
            frame.setVisible(true);
        });
    }
}