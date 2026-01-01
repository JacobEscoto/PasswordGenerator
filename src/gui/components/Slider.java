package gui.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JSlider;
import javax.swing.plaf.basic.BasicSliderUI;

public class Slider extends JSlider {

    private static final Color TRACK_BG = new Color(200, 200, 200);
    private static final Color TRACK_FILL = new Color(33, 150, 243);
    private static final Color THUMB_COLOR = new Color(33, 150, 243);

    public Slider(int min, int max, int currentValue) {
        super(min, max, currentValue);
        setOrientation(HORIZONTAL);
        setFocusable(false);
        initStyle();
    }

    private void initStyle() {
        setPaintTicks(false);
        setPaintLabels(false);
        setOpaque(false);
        setPreferredSize(new Dimension(200, 40));

        setUI(new BasicSliderUI(this) {
            @Override
            public void paintTrack(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                int cy = trackRect.y + trackRect.height / 2 - 3;
                g2.setColor(TRACK_BG);
                g2.fillRoundRect(trackRect.x, cy, trackRect.width, 6, 6, 6);

                int fillWidth = thumbRect.x + thumbRect.width / 2 - trackRect.x;
                g2.setColor(TRACK_FILL);
                g2.fillRoundRect(trackRect.x, cy, fillWidth, 6, 6, 6);
            }

            @Override
            public void paintThumb(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(THUMB_COLOR);
                g2.fillOval(thumbRect.x, thumbRect.y, thumbRect.width, thumbRect.height);
            }

            @Override
            protected Dimension getThumbSize() {
                return new Dimension(16, 16);
            }
        });
    }

}
