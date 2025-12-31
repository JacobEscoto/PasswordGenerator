package gui;

import javax.swing.JFrame;
import logic.*;
import model.PasswordConfig;

public class MainFrame extends JFrame {
    
    private PasswordGenerator generator = new PasswordGenerator();
    private final StrengthAnalyzer analyzer = new StrengthAnalyzer();
    public static PasswordConfig configuration = null;
    
    public MainFrame() {
        initComponents();
        configuration = new PasswordConfig();
    }
    
    private void initComponents() {
        setTitle("Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ConfigPanel configPanel = new ConfigPanel();
        add(configPanel);
    }
    
    public void generatePassword() {
        
    }
    
    public PasswordConfig getConfiguration() {
        return configuration;
    }
    
}
