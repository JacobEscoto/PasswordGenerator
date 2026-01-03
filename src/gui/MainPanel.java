package gui;

import gui.utils.BlueSpaceColors;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class MainPanel extends JPanel {

    private static final int CONTENT_WIDTH = 520;
    private static final int HISTORY_WIDTH = 360;

    public MainPanel(DisplayPanel displayPanel, ConfigPanel configPanel, HistoryPanel historyPanel) {

        setLayout(new BorderLayout(16, 16));
        setBackground(BlueSpaceColors.BG_MAIN);
        setOpaque(true);

        setBorder(BorderFactory.createEmptyBorder(16, 16, 16, 16));

        JPanel centerColumn = new JPanel(new BorderLayout(0, 16));
        centerColumn.setOpaque(false);
        centerColumn.setPreferredSize(new Dimension(CONTENT_WIDTH, 0));

        centerColumn.add(wrapCard(displayPanel), BorderLayout.NORTH);
        centerColumn.add(wrapCard(configPanel), BorderLayout.CENTER);

        JPanel historyWrapper = historyPanel;
        historyWrapper.setPreferredSize(new Dimension(HISTORY_WIDTH, 0));

        add(centerColumn, BorderLayout.CENTER);
        add(historyWrapper, BorderLayout.EAST);
    }

    private JPanel wrapCard(JPanel panel) {
        JPanel card = new JPanel(new BorderLayout());
        card.setOpaque(true);
        card.setBackground(BlueSpaceColors.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BlueSpaceColors.BORDER),
                BorderFactory.createEmptyBorder(12, 12, 12, 12)
        ));
        card.add(panel, BorderLayout.CENTER);
        return card;
    }
}
