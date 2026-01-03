package gui;

import gui.utils.BlueSpaceColors;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

    private static final int CONTENT_WIDTH = 520;

    public MainPanel(DisplayPanel displayPanel, ConfigPanel configPanel) {

        setLayout(new BorderLayout());
        setBackground(BlueSpaceColors.BG_MAIN);
        setOpaque(true);

        setMinimumSize(new Dimension(520, 300));
        setPreferredSize(new Dimension(520, 400));

        JPanel column = new JPanel();
        column.setLayout(new BoxLayout(column, BoxLayout.Y_AXIS));
        column.setOpaque(false);

        column.setAlignmentX(Component.CENTER_ALIGNMENT);

        displayPanel.setAlignmentX(Component.CENTER_ALIGNMENT);
        configPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        column.add(displayPanel);
        column.add(Box.createVerticalStrut(0));
        column.add(configPanel);

        add(column, BorderLayout.CENTER);
        setBorder(BorderFactory.createEmptyBorder(18, 18, 18, 18));
    }
}
