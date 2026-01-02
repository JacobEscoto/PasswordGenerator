package gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JProgressBar;

public class ProgressBar extends JProgressBar {

    private int arc = 16;

    public ProgressBar() {
        setOpaque(false);
        setBorderPainted(false);
        setStringPainted(false);
        setMinimum(0);
        setMaximum(100);
        setPreferredSize(new Dimension(200, 5));
    }

    public void setArc(int arc) {
        this.arc = arc;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        g2.setColor(new Color(220, 220, 220));
        g2.fillRoundRect(0, 0, width, height, arc, arc);

        int progressWidth = (int) (width * (getPercentComplete()));
        if (progressWidth > 0) {
            g2.setColor(getForeground());
            g2.fillRoundRect(0, 0, progressWidth, height, arc, arc);
        }

        g2.dispose();
    }
}
