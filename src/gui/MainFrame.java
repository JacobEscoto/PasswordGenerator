package gui;

import java.awt.BorderLayout;
import javax.swing.JFrame;
import logic.PasswordGenerator;
//import logic.StrengthAnalyzer;
import model.PasswordConfig;

public class MainFrame extends JFrame {
    
    private final PasswordGenerator generator = new PasswordGenerator();
    //private final StrengthAnalyzer analyzer = new StrengthAnalyzer();
    private PasswordConfig configuration;
    private ConfigPanel configPanel;
    private DisplayPanel displayPanel;
    
    public MainFrame() {
        initComponents();
        configuration = new PasswordConfig();
    }
    
    private void initComponents() {
        setLayout(new BorderLayout());
        displayPanel = new DisplayPanel(this);
        configPanel = new ConfigPanel(this);
        add(displayPanel, BorderLayout.NORTH);
        add(configPanel, BorderLayout.CENTER);
        setSize(500, 600);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    public void generatePassword() {
        generatePassword(configPanel.getConfiguration());
    }
    
    public void generatePassword(PasswordConfig config) {
        this.configuration = config;
        String password = generator.generatePassword(configuration);
        displayPanel.setPasswordField(password);
    }
    
}
