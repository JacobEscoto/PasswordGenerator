package app;

import com.formdev.flatlaf.FlatDarkLaf;
import gui.MainFrame;
import java.awt.Color;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class Main {

    public static void main(String[] args) {
        FlatDarkLaf.setup();
        UIManager.put("Component.arc", 12);
        UIManager.put("Button.arc", 12);
        UIManager.put("TextComponent.arc", 10);

        UIManager.put("Component.focusWidth", 0);

        UIManager.put("Component.focusColor", new Color(33, 150, 243));
        UIManager.put("Component.borderColor", new Color(60, 70, 90));

        UIManager.put("Panel.background", new Color(18, 22, 28));
        UIManager.put("Button.background", new Color(30, 36, 46));
        UIManager.put("Button.hoverBackground", new Color(40, 48, 60));
        UIManager.put("Button.pressedBackground", new Color(25, 30, 38));

        UIManager.put("TextField.background", new Color(24, 28, 36));
        UIManager.put("TextField.foreground", new Color(220, 225, 230));
        UIManager.put("TextField.caretForeground", new Color(33, 150, 243));

        UIManager.put("ProgressBar.foreground", new Color(33, 150, 243));
        UIManager.put("Slider.thumbColor", new Color(33, 150, 243));
        UIManager.put("Slider.trackColor", new Color(70, 80, 95));

        SwingUtilities.invokeLater(() -> new MainFrame());
    }

}
