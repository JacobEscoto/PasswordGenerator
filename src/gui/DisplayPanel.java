package gui;

import gui.components.Button;
import gui.components.ProgressBar;
import gui.components.TextField;
import gui.utils.BlueSpaceColors;
import gui.utils.HoverTip;
import gui.utils.Toast;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
import logic.StrengthAnalyzer.StrengthLevel;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class DisplayPanel extends JPanel {

    private static final int HEIGHT = 48;
    private static final int BUTTON_SIZE = 42;
    private static final int GAP = 10;
    private final TextField passwordField;
    private final Button generateBtn;
    private final Button copyBtn;
    private final JPanel buttonsPanel;
    private final MainFrame parent;

    private final ProgressBar progressBar;
    private StrengthLevel level;
    private Timer progressTimer;
    private Color barColor;

    public DisplayPanel(MainFrame parent) {
        this.parent = parent;
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        passwordField = new TextField();
        passwordField.setEditable(false);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setForeground(new Color(30, 30, 30));
        passwordField.setBackground(BlueSpaceColors.BG_CARD);
        setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(0, 0, 0, 0)),
                BorderFactory.createEmptyBorder(14, 16, 14, 16)
        ));
        passwordField.setOpaque(true);
        passwordField.setHorizontalAlignment(SwingConstants.LEFT);
        passwordField.setPreferredSize(new Dimension(300, HEIGHT));
        passwordField.setMinimumSize(new Dimension(220, HEIGHT));

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(0, 0, 0, GAP);
        add(passwordField, c);

        buttonsPanel = new JPanel();
        buttonsPanel.setOpaque(false);
        GridBagLayout bpLayout = new GridBagLayout();
        buttonsPanel.setLayout(bpLayout);

        generateBtn = createButton(FontIcon.of(FontAwesomeSolid.REDO_ALT, 16, Color.WHITE));
        generateBtn.setBackground(BlueSpaceColors.BG_CARD);
        copyBtn = createButton(FontIcon.of(FontAwesomeSolid.COPY, 16, Color.WHITE));
        copyBtn.setBackground(BlueSpaceColors.BG_CARD);

        generateBtn.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));
        copyBtn.setPreferredSize(new Dimension(BUTTON_SIZE, BUTTON_SIZE));

        GridBagConstraints bc = new GridBagConstraints();
        bc.gridx = 0;
        bc.gridy = 0;
        bc.insets = new Insets(0, 0, 0, 6);
        buttonsPanel.add(generateBtn, bc);

        bc.gridx = 1;
        bc.gridy = 0;
        bc.insets = new Insets(0, 6, 0, 0);
        buttonsPanel.add(copyBtn, bc);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 0.0;
        c.fill = GridBagConstraints.NONE;
        c.anchor = GridBagConstraints.EAST;
        add(buttonsPanel, c);

        progressBar = new ProgressBar();
        progressBar.setValue(0);
        progressBar.setPreferredSize(new Dimension(100, 3));
        progressBar.setOpaque(false);

        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        c.weightx = 1.0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(6, 0, 0, GAP);
        add(progressBar, c);

        setListeners();
    }

    private Button createButton(Icon icon) {
        Button b = new Button(10, icon);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void setListeners() {

        generateBtn.addActionListener(e -> parent.generatePassword());
        generateBtn.addMouseListener(new MouseAdapter() {

            private HoverTip tip;

            @Override
            public void mouseEntered(MouseEvent e) {
                Window win = SwingUtilities.getWindowAncestor(generateBtn);
                tip = new HoverTip(win, "Generate password");

                Point p = e.getLocationOnScreen();
                tip.showAt(new Point(p.x, p.y + 24));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tip != null) {
                    tip.hideTip();
                }
            }

        });
        copyBtn.addActionListener(e -> {
            String text = passwordField.getText();
            if (text == null || text.trim().isEmpty()) {
                Toast.showInfo(parent, "Nothing to copy in clipboard");
                return;
            }
            StringSelection selection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, selection);
            Toast.showSuccess(parent, "Password copied to clipboard");
        });
        copyBtn.addMouseListener(new MouseAdapter() {

            private HoverTip tip;

            @Override
            public void mouseEntered(MouseEvent e) {
                Window win = SwingUtilities.getWindowAncestor(copyBtn);
                tip = new HoverTip(win, "Copy password");

                Point p = e.getLocationOnScreen();
                tip.showAt(new Point(p.x, p.y + 24));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (tip != null) {
                    tip.hideTip();
                }
            }
        });
    }

    public void animateProgress(int targetValue) {
        if (barColor == null) {
            barColor = new Color(76, 175, 80);
        }
        if (progressTimer != null && progressTimer.isRunning()) {
            progressTimer.stop();
        }
        progressBar.setValue(0);
        updateProgressBar(targetValue, barColor);
    }

    public void animateProgress(int targetValue, Color color) {
        barColor = color;
        animateProgress(targetValue);
    }

    private void updateProgressBar(int targetValue, Color color) {
        progressBar.setForeground(color);

        if (progressTimer != null && progressTimer.isRunning()) {
            progressTimer.stop();
        }

        int duration = 1000;
        int delay = 15;
        int steps = duration / delay;

        int start = progressBar.getValue();
        int delta = targetValue - start;

        if (delta == 0) {
            return;
        }

        int tempStep = delta / steps;
        if (tempStep == 0) {
            tempStep = delta > 0 ? 1 : -1;
        }
        final int step = tempStep;

        progressTimer = new Timer(delay, e -> {
            int current = progressBar.getValue();

            if ((step > 0 && current >= targetValue)
                    || (step < 0 && current <= targetValue)) {

                progressBar.setValue(targetValue);
                progressTimer.stop();
                return;
            }

            progressBar.setValue(current + step);
        });

        progressTimer.start();
    }

    public void setPasswordField(String password) {
        passwordField.setText(password);
        passwordField.setCaretPosition(0);
        passwordField.repaint();
    }

    public void setLevel(StrengthLevel level) {
        this.level = level;
        progressBar.setToolTipText("Password Strength : " + level.getDescription());
        if (level == null) {
            barColor = new Color(120, 120, 120);
            return;
        }
        barColor = null;
        switch (level) {
            case WEAK ->
                barColor = BlueSpaceColors.RED;
            case MEDIUM ->
                barColor = BlueSpaceColors.ORANGE;
            case STRONG ->
                barColor = BlueSpaceColors.GREEN;
            case VERY_STRONG ->
                barColor = BlueSpaceColors.BLUE_ACCENT;
        }
    }

    public void setFixedWidth(int totalWidth) {
        int buttonsW = (BUTTON_SIZE * 2) + 8;
        int usable = Math.max(220, totalWidth - buttonsW - 24);

        passwordField.setPreferredSize(new Dimension(usable, HEIGHT));
        passwordField.setMaximumSize(new Dimension(usable, HEIGHT));
        buttonsPanel.setPreferredSize(new Dimension(buttonsW, HEIGHT));
        buttonsPanel.setMaximumSize(new Dimension(buttonsW, HEIGHT));

        int progressHeight = progressBar.getPreferredSize().height;
        int totalHeight = HEIGHT + 6 + progressHeight;
        setPreferredSize(new Dimension(totalWidth, totalHeight));
        setMaximumSize(new Dimension(totalWidth, totalHeight));

        revalidate();
        repaint();
    }
}
