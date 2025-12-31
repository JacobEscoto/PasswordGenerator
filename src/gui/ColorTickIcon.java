package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import javax.swing.AbstractButton;
import javax.swing.Icon;

public class ColorTickIcon implements Icon {

    private final Color fillColor;
    private final int size;
    private final int arc;

    public ColorTickIcon(Color fillColor) {
        this(fillColor, 20, 6);
    }

    public ColorTickIcon(Color fillColor, int size, int arc) {
        this.fillColor = fillColor;
        this.size = size;
        this.arc = arc;
    }

    @Override
    public int getIconWidth() {
        return size;
    }

    @Override
    public int getIconHeight() {
        return size;
    }

    @Override
    public void paintIcon(Component component, Graphics g, int x, int y) {
        boolean selected = false;
        boolean enabled = component.isEnabled();

        if (component instanceof AbstractButton b) {
            selected = b.getModel().isSelected();
        }
        Graphics2D g2 = (Graphics2D) g.create();
        try {
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            Color baseGray = new Color(180, 180, 180);
            Color fill = selected ? fillColor : baseGray;
            if (!enabled) {
                fill = new Color(fill.getRed(), fill.getGreen(), fill.getBlue(), 120);
            }

            int pad = 1;
            int w = size - pad * 2;
            int h = size - pad * 2;
            g2.setColor(fill);
            g2.fillRoundRect(x + pad, y + pad, w, h, arc, arc);

            if (selected) {
                Stroke old = g2.getStroke();
                g2.setStroke(new BasicStroke(2.6f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2.setColor(new Color(255, 255, 255));

                int cx = x + size / 2;
                int cy = y + size / 2;
                int checkWidth = size / 2;
                int checkHeight = size / 3;
                int x1 = cx - checkWidth / 2;
                int y1 = cy;
                int x2 = cx - checkWidth / 6;
                int y2 = cy + checkHeight / 2;
                int x3 = cx + checkWidth / 2;
                int y3 = cy - checkHeight / 2;
                g2.drawLine(x1, y1, x2, y2);
                g2.drawLine(x2, y2, x3, y3);
                g2.setStroke(old);
            }
        } finally {
            g2.dispose();
        }
    }
}
