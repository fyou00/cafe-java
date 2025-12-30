import view.MainFrame_old;
import javax.swing.*;

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
            MainFrame_old frame = new MainFrame_old();
            frame.setVisible(true);
        });
    }
}