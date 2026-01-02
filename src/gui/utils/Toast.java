package gui.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.Window;
import java.awt.geom.RoundRectangle2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JWindow;
import javax.swing.Timer;
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
    
    private static final List<Toast> activeToasts = new ArrayList();
    private static final int WIDTH = 300;
    private static final int HEIGHT = 60;
    private static final int GAP = 10;
    private static final int FADE_INTERVAL = 30;
    private static final int DISPLAY_TIME = 2500;

    public Toast(Window owner, String message, Type type) {
        super(owner);
        initUI(message, type);
        pack();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
        setLocationRelativeToOwner(owner);
        fadeIn();
    }
    
    private void initUI(String message, Type type) {
        JPanel panel = new JPanel(new BorderLayout(15, 0));
        panel.setBackground(new Color(255, 255, 255, 240));
        Color borderColor = new Color(219, 219, 219);
        panel.setBorder(new CompoundBorder(new LineBorder(borderColor, 1, true),
                                           new EmptyBorder(12, 16, 12, 16)));

        JLabel iconLabel = new JLabel(FontIcon.of(type.icon, 18, type.color));
        JLabel textLabel = new JLabel("<html><body style='width: " + (WIDTH - 60) + "px;'>" + message + "</body></html>");
        textLabel.setForeground(Color.BLACK);
        textLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        panel.add(iconLabel, BorderLayout.WEST);
        panel.add(textLabel, BorderLayout.CENTER);
        add(panel);
    }

    private void setLocationRelativeToOwner(Window owner) {
        Point loc = owner.getLocationOnScreen();
        int x = loc.x + (owner.getWidth() - getWidth()) / 2;
        int yBase = loc.y + (owner.getHeight() - getHeight()) - 60;

        for (Toast t : activeToasts) {
            yBase -= (t.getHeight() + GAP);
        }
        setLocation(x, yBase);
        activeToasts.add(this);
    }
    
    private void fadeIn() {
        setOpacity(0f);
        setVisible(true);
        Timer timer = new Timer(FADE_INTERVAL, null);
        timer.addActionListener(e -> {
            float opacity = getOpacity();
            opacity += 0.1f;
            if (opacity >= 1f) {
                setOpacity(1f);
                ((Timer) e.getSource()).stop();
                stayVisible();
            } else {
                setOpacity(opacity);
            }
        });
        timer.start();
    }
    
    private void stayVisible() {
        Timer stay = new Timer(DISPLAY_TIME, e -> fadeOut());
        stay.setRepeats(false);
        stay.start();
    }

    private void fadeOut() {
        Timer timer = new Timer(FADE_INTERVAL, null);
        timer.addActionListener(e -> {
            float opacity = getOpacity();
            opacity -= 0.1f;
            if (opacity <= 0f) {
                setOpacity(0f);
                ((Timer) e.getSource()).stop();
                dispose();
                activeToasts.remove(this);
            } else {
                setOpacity(opacity);
            }
        });
        timer.start();
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
