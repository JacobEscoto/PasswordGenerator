package gui;

import gui.components.ColorTickIcon;
import gui.components.Slider;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import model.PasswordConfig;

public class ConfigPanel extends JPanel {

    private static final int ROW_HEIGHT = 40;
    private static final int GAP = 10;

    private final Slider slider;
    private final JLabel lengthLabel;
    private final JCheckBox lowerCase;
    private final JCheckBox upperCase;
    private final JCheckBox numbers;
    private final JCheckBox symbols;
    private final MainFrame parent;

    public ConfigPanel(MainFrame parent) {
        this.parent = parent;
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 0, 6, 0);
        c.fill = GridBagConstraints.HORIZONTAL;

        lengthLabel = new JLabel("10", SwingConstants.CENTER);
        lengthLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        lengthLabel.setPreferredSize(new Dimension(36, ROW_HEIGHT));

        slider = new Slider(1, 50, 10);
        slider.setPreferredSize(new Dimension(200, ROW_HEIGHT));
        slider.addChangeListener(e -> {
            lengthLabel.setText(String.valueOf(slider.getValue()));
            if (!slider.getValueIsAdjusting()) {
                autoGenerate();
            }
        });

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        c.anchor = GridBagConstraints.WEST;
        add(lengthLabel, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        c.insets = new Insets(6, GAP, 6, 0);
        add(slider, c);

        lowerCase = createCheckBox("Lowercase");
        upperCase = createCheckBox("Uppercase");
        numbers = createCheckBox("Numbers");
        symbols = createCheckBox("Symbols");
        lowerCase.setSelected(true);

        JPanel ckPanel = new JPanel();
        ckPanel.setOpaque(false);
        ckPanel.add(lowerCase);
        ckPanel.add(upperCase);
        ckPanel.add(numbers);
        ckPanel.add(symbols);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.weightx = 1.0;
        c.insets = new Insets(6, 0, 0, 0);
        add(ckPanel, c);
    }

    private void autoGenerate() {
        if (lowerCase.isSelected() || upperCase.isSelected() || numbers.isSelected() || symbols.isSelected()) {
            parent.generatePassword(getConfiguration());
        }
    }

    private JCheckBox createCheckBox(String text) {
        JCheckBox cb = new JCheckBox(text);
        cb.setIcon(new ColorTickIcon(Color.decode("#2196F3"), 16, 5));
        cb.setSelectedIcon(new ColorTickIcon(Color.decode("#2196F3"), 16, 5));
        cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        cb.addActionListener(e -> autoGenerate());
        cb.setFocusPainted(false);
        cb.setContentAreaFilled(false);
        cb.setBorderPainted(false);
        cb.setOpaque(false);
        return cb;
    }

    public PasswordConfig getConfiguration() {
        PasswordConfig cfg = new PasswordConfig();
        cfg.setLength(slider.getValue());
        cfg.setIncludesLower(lowerCase.isSelected());
        cfg.setIncludesUpper(upperCase.isSelected());
        cfg.setIncludesNumbers(numbers.isSelected());
        cfg.setIncludesSymbols(symbols.isSelected());
        return cfg;
    }

    public void setFixedWidth(int totalWidth) {
        int paddingEstimate = 24;
        int usableWidth = Math.max(220, totalWidth - paddingEstimate - 36);
        slider.setPreferredSize(new Dimension(usableWidth, ROW_HEIGHT));
        slider.setMaximumSize(new Dimension(usableWidth, ROW_HEIGHT));

        setPreferredSize(new Dimension(totalWidth, ROW_HEIGHT * 2 + 20));
        setMaximumSize(new Dimension(totalWidth, ROW_HEIGHT * 2 + 20));

        revalidate();
        repaint();
    }
}
