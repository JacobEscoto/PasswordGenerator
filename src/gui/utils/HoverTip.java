package gui.utils;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import javax.swing.*;

public class HoverTip extends JWindow {

    private Timer fadeTimer;
    private float opacity = 0f;

    public HoverTip(Window owner, String text) {
        super(owner);
        setFocusableWindowState(false);
        setAlwaysOnTop(true);

        JLabel label = new JLabel(text);
        label.setFont(new Font("SansSerif", Font.PLAIN, 12));
        label.setForeground(Color.WHITE);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(40, 40, 40));
        panel.setBorder(BorderFactory.createEmptyBorder(6, 10, 6, 10));
        panel.add(label);

        add(panel);
        pack();

        setShape(new RoundRectangle2D.Double(
                0, 0, getWidth(), getHeight(), 12, 12
        ));
    }

    public void showAt(Point location) {
        setOpacity(0f);
        setLocation(location);
        setVisible(true);

        fadeTimer = new Timer(15, e -> {
            opacity += 0.08f;
            if (opacity >= 1f) {
                opacity = 1f;
                fadeTimer.stop();
            }
            setOpacity(opacity);
        });
        fadeTimer.start();
    }

    public void hideTip() {
        if (fadeTimer != null) {
            fadeTimer.stop();
        }

        fadeTimer = new Timer(15, e -> {
            opacity -= 0.08f;
            if (opacity <= 0f) {
                fadeTimer.stop();
                dispose();
            }
            setOpacity(Math.max(opacity, 0f));
        });
        fadeTimer.start();
    }
}
