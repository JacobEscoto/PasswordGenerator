package app;

import gui.MainFrame;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.pack();
            frame.setSize(new Dimension(450, 200));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }

}
