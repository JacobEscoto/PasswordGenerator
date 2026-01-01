package gui;

import gui.components.Button;
import gui.components.TextField;
import gui.utils.Toast;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
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

    public DisplayPanel(MainFrame parent) {
        this.parent = parent;
        setOpaque(false);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        passwordField = new TextField();
        passwordField.setEditable(false);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        passwordField.setForeground(new Color(30,30,30));
        passwordField.setBackground(Color.WHITE);
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

        generateBtn = createButton(FontIcon.of(FontAwesomeSolid.REDO_ALT, 16, Color.DARK_GRAY));
        copyBtn = createButton(FontIcon.of(FontAwesomeSolid.COPY, 16, Color.DARK_GRAY));

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

        setListeners();
    }

    private Button createButton(Icon icon) {
        Button b = new Button(10, icon);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return b;
    }

    private void setListeners() {
        generateBtn.addActionListener(e -> parent.generatePassword());
        copyBtn.addActionListener(e -> {
            String text = passwordField.getText();
            if (text == null || text.trim().isEmpty()) {
                Toast.showInfo(parent, "Nothing to copy in clipboard");
                return;
            }
            StringSelection selection = new StringSelection(text);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(selection, null);
            Toast.showSuccess(parent, "Password copied to clipboard");
        });
    }

    public void setPasswordField(String password) {
        passwordField.setText(password);
        passwordField.setCaretPosition(0);
        passwordField.repaint();
    }

    public void setFixedWidth(int totalWidth) {
        int buttonsW = (BUTTON_SIZE * 2) + 8;
        int usable = Math.max(220, totalWidth - buttonsW - 24);

        passwordField.setPreferredSize(new Dimension(usable, HEIGHT));
        passwordField.setMaximumSize(new Dimension(usable, HEIGHT));

        buttonsPanel.setPreferredSize(new Dimension(buttonsW, HEIGHT));
        buttonsPanel.setMaximumSize(new Dimension(buttonsW, HEIGHT));

        setPreferredSize(new Dimension(totalWidth, HEIGHT + 12));
        setMaximumSize(new Dimension(totalWidth, HEIGHT + 12));

        revalidate();
        repaint();
    }
}
