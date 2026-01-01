package gui.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TextField extends JTextField {

    private final Color lineColor = new Color(200, 200, 200);
    private final Color focusColor = new Color(33, 150, 243);
    private final int lineHeight = 1;
    private final int focusLineHeight = 2;

    public TextField() {
        setOpaque(false);
        setBorder(new EmptyBorder(8, 4, 8, 4));
        setForeground(new Color(60, 60, 60));
        setCaretColor(focusColor);
        setSelectionColor(new Color(187, 222, 251));
        setBackground(Color.WHITE);

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                setCaretColor(new Color(0, 0, 0, 0));
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                setCaretColor(new Color(0, 0, 0, 0));
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        int y = getHeight() - 2;

        if (hasFocus()) {
            g2.setColor(focusColor);
            g2.fillRect(0, y, getWidth(), focusLineHeight);
        } else {
            g2.setColor(lineColor);
            g2.fillRect(0, y, getWidth(), lineHeight);
        }

        g2.dispose();
    }

}
