package gui.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.UIManager;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class Toast extends JWindow {

    public enum Type {
        SUCCESS(new Color(76, 175, 80), FontAwesomeSolid.CHECK_CIRCLE),
        ERROR(new Color(244, 67, 54), FontAwesomeSolid.EXCLAMATION_CIRCLE),
        INFO(new Color(33, 150, 243), FontAwesomeSolid.INFO_CIRCLE);

        final Color color;
        final Ikon icon;

        Type(Color color, Ikon icon) {
            this.color = color;
            this.icon = icon;
        }
    }

    public Toast(Window owner, String message, Type type) {
        super(owner);
        setLayout(new BorderLayout());
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(UIManager.getColor("Notification.background"));

        Color borderColor = UIManager.getColor("Notification.borderColor");
        if (borderColor == null) {
            borderColor = new Color(219, 219, 219);
        }

        panel.setBorder(new CompoundBorder(new LineBorder(borderColor, 1, true), new EmptyBorder(12, 20, 12, 20)));

        JLabel iconLabel = new JLabel(FontIcon.of(type.icon, 18, type.color));
        JLabel textLabel = new JLabel("<html><body style='width: 200px;'>" + message + "</body></html>");
        textLabel.setForeground(UIManager.getColor("Notification.foreground"));
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textLabel, BorderLayout.CENTER);

        add(panel);
        pack();

        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        Point loc = owner.getLocationOnScreen();
        int x = loc.x + (owner.getWidth() - getWidth()) / 2;
        int y = loc.y + (owner.getHeight() - getHeight()) - 60;
        setLocation(x, y);

        new Thread(() -> {
            try {
                setOpacity(0f);
                setVisible(true);

                for (float index = 0; index <= 1.0f; index += 0.2f) {
                    setOpacity(index);
                    Thread.sleep(20);
                }
                Thread.sleep(2500);

                for (float index = 1.0f; index >= 0; index -= 0.1f) {
                    setOpacity(index);
                    Thread.sleep(30);
                }

                dispose();
            } catch (InterruptedException ex) {
            }
        }).start();
    }

    public static void show(Window owner, String message, Type type) {
        if (owner == null || !owner.isShowing()) {
            return;
        }
        new Toast(owner, message, type);
    }

    public static void showInfo(Window owner, String message) {
        show(owner, message, Type.INFO);
    }

    public static void showError(Window owner, String message) {
        show(owner, message, Type.ERROR);
    }

    public static void showSuccess(Window owner, String message) {
        show(owner, message, Type.SUCCESS);
    }
}
