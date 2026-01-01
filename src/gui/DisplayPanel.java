package gui;

import gui.components.TextField;
import gui.utils.Toast;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JPanel;
import javax.swing.JButton;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class DisplayPanel extends JPanel {

    private TextField passwordField;
    private JButton generateBtn;
    private JButton copyBtn;
    private final MainFrame parent;

    public DisplayPanel(MainFrame parent) {
        this.parent = parent;
        initComponents();
        setListeners();
    }

    private void initComponents() {
        setBackground(Color.WHITE);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));

        passwordField = new TextField();
        passwordField.setEditable(false);
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        buttonPanel.setBackground(Color.WHITE);
        
        generateBtn = createButton(FontIcon.of(FontAwesomeSolid.REDO_ALT, 14, new Color(172, 172, 172)));
        generateBtn.setToolTipText("Generate Password");
        
        copyBtn = createButton(FontIcon.of(FontAwesomeSolid.COPY, 16, Color.GRAY));
        copyBtn.setToolTipText("Copy Password");
        
        buttonPanel.add(generateBtn);
        buttonPanel.add(copyBtn);
        
        add(passwordField, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.EAST);
    }
    
    private JButton createButton(Icon icon) {
        JButton button = new JButton(icon);
        button.setBorder(null);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setFocusable(false);
        button.setContentAreaFilled(false);
        return button;
    }
    
    public void setPasswordField(String password) {
        passwordField.setText(password);
        passwordField.setEditable(false);
    }
    
    
    private void setListeners() {
        generateBtn.addActionListener(e -> parent.generatePassword());
        copyBtn.addActionListener(e -> {
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            StringSelection selection = new StringSelection(passwordField.getText());
            
            if (selection.toString().trim().isEmpty()) {
                Toast.showInfo(parent, "Nothing to copy in clipboard");
                return;
            }
            clipboard.setContents(selection, selection);
            Toast.showSuccess(parent, "Password copied to clipboard");
        });
    }

}
