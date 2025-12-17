import javax.swing.*;

/**
 *
 * @author muham
 */
public class TestSwing {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Form Sederhana");

        // TextField untuk input
        JTextField textField = new JTextField();
        textField.setBounds(50, 40, 200, 30);

        // Tombol
        JButton button = new JButton("Tampilkan");
        button.setBounds(90, 90, 120, 30);

        // Label untuk output
        JLabel label = new JLabel("Hasil akan muncul di sini");
        label.setBounds(50, 140, 200, 30);

        // Masukkan komponen ke frame
        frame.add(textField);
        frame.add(button);
        frame.add(label);

        // Pengaturan frame
        frame.setSize(300, 250);
        frame.setLayout(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    
}
