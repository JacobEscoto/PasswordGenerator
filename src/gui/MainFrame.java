package gui;

import gui.utils.Toast;
import java.awt.Color;
import javax.swing.JFrame;
import logic.PasswordGenerator;
import logic.StrengthAnalyzer;
import model.PasswordConfig;

public class MainFrame extends JFrame {

    private static final int CONTENT_WIDTH = 520;

    private final PasswordGenerator generator = new PasswordGenerator();
    private PasswordConfig configuration;
    private final StrengthAnalyzer analyzer = new StrengthAnalyzer();
    private ConfigPanel configPanel;
    private DisplayPanel displayPanel;

    public MainFrame() {
        configuration = new PasswordConfig();
        initComponents();
    }

    private void initComponents() {
        setTitle("— Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);       
        displayPanel = new DisplayPanel(this);
        configPanel = new ConfigPanel(this);

        MainPanel mainPanel = new MainPanel(displayPanel, configPanel, CONTENT_WIDTH);
        setContentPane(mainPanel);
        setBackground(Color.WHITE);
        pack();
        setLocationRelativeTo(null);
        setMinimumSize(getSize());
        setVisible(true);
        
        generatePassword(configPanel.getConfiguration());
    }

    public void generatePassword() {
        generatePassword(configPanel.getConfiguration());
    }

    public void generatePassword(PasswordConfig config) {
        if (config == null) {
            Toast.showError(this, "Invalid Configuration... Try again");
            return;
        }
        this.configuration = config;
        String password = generator.generatePassword(configuration);
        displayPanel.setPasswordField(password);
    }

}
