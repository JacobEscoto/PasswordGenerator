package gui.utils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
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
        SUCCESS(BlueSpaceColors.GREEN, FontAwesomeSolid.CHECK_CIRCLE),
        ERROR(BlueSpaceColors.RED, FontAwesomeSolid.EXCLAMATION_CIRCLE),
        INFO(BlueSpaceColors.BLUE_ACCENT, FontAwesomeSolid.INFO_CIRCLE);

        public final Color color;
        public final Ikon icon;

        Type(Color color, Ikon icon) {
            this.color = color;
            this.icon = icon;
        }
    }

    public enum Position {
        BOTTOM_CENTER,
        TOP_CENTER,
        TOP_RIGHT,
        BOTTOM_RIGHT
    }

    private static final List<Toast> activeToasts = new ArrayList();

    private static final int WIDTH = 320;
    private static final int GAP = 10;

    private static final int FADE_INTERVAL = 15;
    private static final int DISPLAY_TIME = 2500;
    private static final int ARC = 14;

    private final Type type;
    private final Position position;

    private Toast(Window owner, String message, Type type, Position position) {
        super(owner);
        this.type = type;
        this.position = position;
        initUI(message);
        pack();
        setShape(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), ARC, ARC));
        placeAndStack();
        fadeIn();
    }

    private void initUI(String message) {
        JPanel panel = new JPanel(new BorderLayout(12, 0));
        panel.setBackground(BlueSpaceColors.BG_CARD);
        panel.setBorder(new CompoundBorder(new LineBorder(BlueSpaceColors.BORDER, 1, true),
                new EmptyBorder(10, 12, 10, 12)));
        panel.setPreferredSize(new Dimension(WIDTH, 56));

        JLabel iconLabel = new JLabel(FontIcon.of(type.icon, 18, type.color));
        panel.add(iconLabel, BorderLayout.WEST);

        JLabel textLabel = new JLabel("<html><body style='width: " + (WIDTH - 96) + "px;'>" + message + "</body></html>");
        textLabel.setForeground(BlueSpaceColors.TEXT_MAIN);
        textLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        panel.add(textLabel, BorderLayout.CENTER);

        add(panel);
    }

    private void placeAndStack() {
        Window owner = getOwner();
        Point loc = owner.getLocationOnScreen();
        int ownerW = owner.getWidth();
        int ownerH = owner.getHeight();

        synchronized (activeToasts) {
            activeToasts.add(this);
        }
        int index;
        synchronized (activeToasts) {
            index = activeToasts.indexOf(this);
        }

        int x = 0, y = 0;
        int toastW = getPreferredSize().width;
        int toastH = getPreferredSize().height;

        int margin = 20;

        switch (position) {
            case TOP_RIGHT -> {
                x = loc.x + ownerW - toastW - margin;
                y = loc.y + margin + index * (toastH + GAP);
            }
            case TOP_CENTER -> {
                x = loc.x + (ownerW - toastW) / 2;
                y = loc.y + margin + index * (toastH + GAP);
            }
            case BOTTOM_RIGHT -> {
                x = loc.x + ownerW - toastW - margin;
                y = loc.y + ownerH - toastH - margin - index * (toastH + GAP);
            }
            case BOTTOM_CENTER -> {
                x = loc.x + (ownerW - toastW) / 2;
                y = loc.y + ownerH - toastH - margin - index * (toastH + GAP);
            }
        }

        setLocation(x, y);
    }

    private void fadeIn() {
        setOpacity(0f);
        setVisible(true);

        Timer fadeIn = new Timer(FADE_INTERVAL, null);
        fadeIn.addActionListener(e -> {
            float op = getOpacity() + 0.08f;
            if (op >= 1f) {
                setOpacity(1f);
                ((Timer) e.getSource()).stop();
                stayVisible();
            } else {
                setOpacity(op);
            }
        });
        fadeIn.start();
    }

    private void stayVisible() {
        Timer stay = new Timer(DISPLAY_TIME, e -> fadeOut());
        stay.setRepeats(false);
        stay.start();
    }

    private void fadeOut() {
        Timer fadeOut = new Timer(FADE_INTERVAL, null);
        fadeOut.addActionListener(e -> {
            float op = getOpacity() - 0.07f;
            if (op <= 0f) {
                setOpacity(0f);
                ((Timer) e.getSource()).stop();
                closeAndReposition();
            } else {
                setOpacity(op);
            }
        });
        fadeOut.start();
    }

    private void closeAndReposition() {
        synchronized (activeToasts) {
            activeToasts.remove(this);
        }
        dispose();

        synchronized (activeToasts) {
            for (int i = 0; i < activeToasts.size(); i++) {
                Toast t = activeToasts.get(i);
                t.repositionByIndex(i);
            }
        }
    }

    private void repositionByIndex(int index) {
        Window owner = getOwner();
        Point loc = owner.getLocationOnScreen();
        int ownerW = owner.getWidth();
        int ownerH = owner.getHeight();

        int toastW = getPreferredSize().width;
        int toastH = getPreferredSize().height;
        int margin = 20;

        int x = 0, y = 0;
        switch (position) {
            case TOP_RIGHT -> {
                x = loc.x + ownerW - toastW - margin;
                y = loc.y + margin + index * (toastH + GAP);
            }
            case TOP_CENTER -> {
                x = loc.x + (ownerW - toastW) / 2;
                y = loc.y + margin + index * (toastH + GAP);
            }
            case BOTTOM_RIGHT -> {
                x = loc.x + ownerW - toastW - margin;
                y = loc.y + ownerH - toastH - margin - index * (toastH + GAP);
            }
            case BOTTOM_CENTER -> {
                x = loc.x + (ownerW - toastW) / 2;
                y = loc.y + ownerH - toastH - margin - index * (toastH + GAP);
            }
        }
        setLocation(x, y);
    }

    public static void show(Window owner, String message, Type type) {
        show(owner, message, type, Position.BOTTOM_CENTER);
    }
    
    public static void show(Window owner, String message, Type type, Position pos) {
        if (owner == null || !owner.isShowing()) {
            return;
        }
        new Toast(owner, message, type, pos);
    }

    public static void showInfo(Window owner, String message) {
        show(owner, message, Type.INFO);
    }

    public static void showInfo(Window owner, String message, Position pos) {
        show(owner, message, Type.INFO, pos);
    }

    public static void showError(Window owner, String message) {
        show(owner, message, Type.ERROR);
    }

    public static void showError(Window owner, String message, Position pos) {
        show(owner, message, Type.ERROR, pos);
    }

    public static void showSuccess(Window owner, String message) {
        show(owner, message, Type.SUCCESS);
    }

    public static void showSuccess(Window owner, String message, Position pos) {
        show(owner, message, Type.SUCCESS, pos);
    }
}
