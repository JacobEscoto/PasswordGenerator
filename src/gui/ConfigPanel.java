package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class ConfigPanel extends JPanel {

    private static final Color BG_COLOR = new Color(241, 241, 241);
    private static final Color TEXT_COLOR = new Color(33, 33, 33);
    private static final int MAX_WIDTH = 450;
    private static final int ROW_HEIGHT = 40;
    private static final int GAP = 10;

    private JCheckBox upperCaseCheckBox;
    private JCheckBox lowerCaseCheckBox;
    private JCheckBox numberCheckBox;
    private JCheckBox symbolsCheckBox;
    private JLabel lengthLabel;
    private Slider slider;

    public ConfigPanel() {
        initComponents();
    }

    private void initComponents() {
        setLayout(new BorderLayout());
        setBackground(BG_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        setMaximumSize(new Dimension(MAX_WIDTH, 100));

        add(createContent(), BorderLayout.CENTER);
    }

    private JComponent createContent() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBackground(BG_COLOR);

        panel.add(createLengthRow());
        panel.add(Box.createVerticalStrut(8));
        panel.add(createOptionsRow());

        return panel;
    }

    private JComponent createLengthRow() {
        JPanel row = createRowPanel();

        lengthLabel = new JLabel("10", SwingConstants.CENTER);
        lengthLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        lengthLabel.setForeground(TEXT_COLOR);
        lengthLabel.setPreferredSize(new Dimension(30, ROW_HEIGHT));

        slider = new Slider(1, 50, 10);
        slider.setMaximumSize(new Dimension(300, ROW_HEIGHT));
        slider.addChangeListener(e
                -> lengthLabel.setText(String.valueOf(slider.getValue()))
        );

        row.add(lengthLabel);
        row.add(Box.createHorizontalStrut(GAP));
        row.add(slider);

        return row;
    }
    
    private JComponent createOptionsRow() {
        JPanel row = createRowPanel();

        lowerCaseCheckBox = createCheckBox("Lowercase");
        upperCaseCheckBox = createCheckBox("Uppercase");
        numberCheckBox = createCheckBox("Numbers");
        symbolsCheckBox = createCheckBox("Symbols");

        row.add(lowerCaseCheckBox);
        row.add(Box.createHorizontalStrut(GAP));
        row.add(upperCaseCheckBox);
        row.add(Box.createHorizontalStrut(GAP));
        row.add(numberCheckBox);
        row.add(Box.createHorizontalStrut(GAP));
        row.add(symbolsCheckBox);

        return row;
    }
    
    private JPanel createRowPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
        panel.setBackground(BG_COLOR);
        panel.setAlignmentX(LEFT_ALIGNMENT);
        panel.setMaximumSize(new Dimension(MAX_WIDTH, ROW_HEIGHT));
        return panel;
    }
    
    private JCheckBox createCheckBox(String text) {
        JCheckBox cb = new JCheckBox(text);
        cb.setOpaque(false);
        cb.setFocusPainted(false);
        cb.setBorderPainted(false);
        cb.setContentAreaFilled(false);
        cb.setFocusable(false);
        cb.setIcon(new ColorTickIcon(Color.decode("#2196F3"), 18, 6));
        cb.setSelectedIcon(new ColorTickIcon(Color.decode("#2196F3"), 18, 6));
        cb.setHorizontalTextPosition(SwingConstants.RIGHT);
        cb.setForeground(TEXT_COLOR);
        cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cb.setIconTextGap(10);
        return cb;
    }

}
