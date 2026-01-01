package gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;
import javax.swing.Icon;
import javax.swing.JButton;

public class Button extends JButton {

    private int radius;

    public Button(int radius, Icon icon) {
        this.radius = radius;
        setIcon(icon);
        setBorderPainted(false);
        setFocusPainted(false);
        setContentAreaFilled(false);
        setOpaque(false);
        setFocusable(false);

        setBackground(new Color(230, 230, 230));
        setForeground(Color.DARK_GRAY);
    }

    public Button(int radius) {
        this(radius, null);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        int width = getWidth();
        int height = getHeight();
        Color bg = getBackground();

        if (!isEnabled()) {
            bg = bg.darker();
        } else if (getModel().isPressed()) {
            bg = bg.darker();
        } else if (getModel().isRollover()) {
            bg = Color.decode("#D9D9D9");
        }
        g2.setColor(bg);
        g2.fillRoundRect(0, 0, width, height, radius, radius);

        g2.dispose();
        super.paintComponent(g);
    }

    @Override
    public boolean contains(int x, int y) {
        Shape shape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), radius, radius);
        return shape.contains(x, y);
    }

}
