package gui.components;

import gui.utils.BlueSpaceColors;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

public class TextField extends JTextField {

    private static final Color BORDER_IDLE = new Color(60, 70, 90);
    private static final Color SELECTION = new Color(33, 150, 243, 90);

    private static final int LINE_HEIGHT = 1;
    private static final int FOCUS_HEIGHT = 2;

    public TextField() {
        setOpaque(false);

        setBackground(BlueSpaceColors.BG_CARD);

        setCaretColor(new Color(0, 0, 0, 0));
        setSelectionColor(SELECTION);
        setSelectedTextColor(Color.WHITE);

        setBorder(new EmptyBorder(10, 6, 10, 6));
        setFont(getFont().deriveFont(Font.PLAIN, 14f));

        addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                repaint();
            }

            @Override
            public void focusLost(FocusEvent e) {
                repaint();
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(BlueSpaceColors.BG_CARD);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

        super.paintComponent(g2);

        setForeground(BlueSpaceColors.TEXT_MAIN);

        super.paintComponent(g2);

        int y = getHeight() - 2;

        g2.setColor(hasFocus() ? BlueSpaceColors.BLUE_ACCENT : BORDER_IDLE);
        g2.fillRect(8, y, getWidth() - 16, hasFocus() ? FOCUS_HEIGHT : LINE_HEIGHT);

        g2.dispose();
    }
}
