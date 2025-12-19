import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

/**
 *
 * @author muham
 */
public class Main {
    public static void main(String[] args) {
        String[] categories = {"Semua", "Kopi", "Non Kopi", "Makanan"};
        JComboBox<String> comboBox = new JComboBox<>(categories);
        
        String[][] menuData = {
            {"Espresso", "Americano", "Nasi Goreng", "Brownies"}, // Semua
            {"Espresso", "Americano", "Cappuccino"},              // Kopi
            {"Matcha Latte", "Thai Tea", "Chocolate"},            // Non Kopi
            {"Nasi Goreng", "Mie Goreng", "French Fries"}         // Makanan
        };
        
        comboBox.addActionListener((ActionEvent e) -> {
            int index = comboBox.getSelectedIndex();
            
            // Clear list dulu
            listModel.clear();
            
            // Add items berdasarkan kategori
            for (String item : menuData[index]) {
                listModel.addElement(item);
            }
        });
        
        JFrame frame = new Frame();
        
        frame.setVisible(true);
    }
    
}
