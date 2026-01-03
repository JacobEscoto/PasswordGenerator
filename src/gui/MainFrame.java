package gui;

import gui.utils.Toast;
import java.awt.Dimension;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
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

        historyManager = new HistoryManager();
        displayPanel = new DisplayPanel(this);
        configPanel = new ConfigPanel(this);
        historyPanel = new HistoryPanel(this);

        historyPanel.refresh(historyManager.getHistory());

        MainPanel leftPanel = new MainPanel(displayPanel, configPanel);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, historyPanel);

        splitPane.setResizeWeight(1.0);
        splitPane.setDividerSize(1);
        splitPane.setDividerLocation(560);
        splitPane.setContinuousLayout(true);
        splitPane.setEnabled(false);

        setContentPane(splitPane);
        pack();
        setMinimumSize(new Dimension(960, 520));
        setLocationRelativeTo(null);
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

        StrengthResult result = analyzer.checkPasswordStrength(password);
        displayPanel.setLevel(result.getLevel());
        displayPanel.animateProgress(result.getScore());

        addToHistory(password, result.getLevel());
    }

    private void addToHistory(String password, StrengthLevel level) {
        String formattedDate = ZonedDateTime.now(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH));
        historyManager.addPassword(new Password(password, formattedDate, level.getDescription()));

        updateHistory();
    }

    private void updateHistory() {
        historyPanel.refresh(historyManager.getHistory());
    }

    public HistoryManager getHistoryManager() {
        return historyManager;
    }
}
