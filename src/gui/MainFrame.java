package gui;

import gui.utils.Toast;
import gui.utils.Toast.Position;
import java.awt.Dimension;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
import logic.HistoryManager;
import logic.PasswordGenerator;
import logic.StrengthAnalyzer;
import logic.StrengthAnalyzer.StrengthLevel;
import logic.StrengthAnalyzer.StrengthResult;
import model.Password;
import model.PasswordConfig;

public class MainFrame extends JFrame {

    private HistoryManager historyManager;
    private HistoryPanel historyPanel;

    private final PasswordGenerator generator = new PasswordGenerator();
    private final StrengthAnalyzer analyzer = new StrengthAnalyzer();

    private PasswordConfig configuration;
    private ConfigPanel configPanel;
    private DisplayPanel displayPanel;

    public MainFrame() {
        initComponents();
    }

    private void initComponents() {
        setTitle("— Password Generator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        historyManager = new HistoryManager();

        displayPanel = new DisplayPanel(this);
        configPanel = new ConfigPanel(this);
        historyPanel = new HistoryPanel(this);

        historyPanel.refresh(historyManager.getHistory());

        MainPanel mainPanel = new MainPanel(displayPanel, configPanel, historyPanel);
        setContentPane(mainPanel);
        configuration = configPanel.getConfiguration();
        
        pack();
        setMinimumSize(new Dimension(1080, 520));
        setLocationRelativeTo(null);
        setVisible(true);
        
        generatePassword(configuration);
    }

    public void generatePassword() {
        generatePassword(configPanel.getConfiguration());
    }

    public void generatePassword(PasswordConfig config) {
        if (config == null) {
            Toast.showError(this, "Invalid configuration", Position.BOTTOM_RIGHT);
            return;
        }

        this.configuration = config;

        String password = generator.generatePassword(configuration);
        displayPanel.setPasswordField(password);

        StrengthResult result = analyzer.checkPasswordStrength(password);
        displayPanel.setLevel(result.getLevel());
        displayPanel.animateProgress(result.getScore());

        addToHistory(password, result.getLevel());
    }

    private void addToHistory(String password, StrengthLevel level) {
        String date = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("MMM dd, yyyy • HH:mm", Locale.ENGLISH));
        historyManager.addPassword(new Password(password, date, level.getDescription()));
        updateHistory();
    }

    private void updateHistory() {
        historyPanel.refresh(historyManager.getHistory());
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
