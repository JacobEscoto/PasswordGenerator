package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagLayout;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

    public MainPanel(DisplayPanel displayPanel, ConfigPanel configPanel, int contentWidth) {
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(new Color(245, 246, 247));

        JPanel center = new JPanel(new GridBagLayout());
        center.setOpaque(false);

        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setOpaque(true);
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 220, 220)),
                BorderFactory.createEmptyBorder(18, 18, 18, 18)
        ));

        card.add(displayPanel);
        card.add(Box.createVerticalStrut(18));
        card.add(configPanel);

        displayPanel.setFixedWidth(contentWidth);
        configPanel.setFixedWidth(contentWidth);

        int totalHeight = displayPanel.getPreferredSize().height + 18 + configPanel.getPreferredSize().height;
        card.setPreferredSize(new Dimension(contentWidth, totalHeight));
        card.setMaximumSize(new Dimension(contentWidth, Integer.MAX_VALUE));
        card.setAlignmentX(Component.CENTER_ALIGNMENT);

        center.add(card);
        add(center, BorderLayout.CENTER);

        // padding exterior opcional
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    }
}
