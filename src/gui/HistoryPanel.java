package gui;

import gui.components.Button;
import gui.utils.BlueSpaceColors;
import gui.utils.HoverTip;
import gui.utils.Toast;
import gui.utils.Toast.Position;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import model.Password;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class HistoryPanel extends JPanel {

    private static final int PANEL_WIDTH = 360;
    private static final int MAX_PWD_CHARS = 28;

    private final JPanel listPanel;
    private final JScrollPane scrollPane;
    private final MainFrame parent;

    public HistoryPanel(MainFrame parent) {
        this.parent = parent;

        setLayout(new BorderLayout());
        setBackground(BlueSpaceColors.BG_MAIN);
        setOpaque(true);

        Dimension fixed = new Dimension(PANEL_WIDTH, 0);
        setPreferredSize(fixed);
        setMinimumSize(fixed);
        setMaximumSize(new Dimension(PANEL_WIDTH, Integer.MAX_VALUE));

        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(14);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setOpaque(false);

        add(scrollPane, BorderLayout.CENTER);
    }

    public void refresh(List<Password> history) {
        listPanel.removeAll();

        if (history == null || history.isEmpty()) {
            JLabel empty = new JLabel("No passwords yet");
            empty.setForeground(BlueSpaceColors.TEXT_MUTED);
            empty.setBorder(BorderFactory.createEmptyBorder(16, 8, 16, 8));
            listPanel.add(empty);
        } else {
            for (int i = history.size() - 1; i >= 0; i--) {
                listPanel.add(createCard(history.get(i)));
                listPanel.add(Box.createVerticalStrut(10));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createCard(Password p) {
        JPanel card = new JPanel(new BorderLayout(10, 0));
        card.setOpaque(true);
        card.setBackground(BlueSpaceColors.BG_CARD);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BlueSpaceColors.BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 56));

        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);

        String fullPwd = p.getPassword();
        String shortPwd = shorten(fullPwd, MAX_PWD_CHARS);

        JLabel pwdLabel = new JLabel(shortPwd);
        pwdLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        pwdLabel.setForeground(BlueSpaceColors.TEXT_MAIN);
        pwdLabel.setToolTipText(fullPwd);

        JLabel meta = new JLabel(p.getDateCreated() + " • " + p.getStrengthLevel());
        meta.setFont(new Font("SansSerif", Font.PLAIN, 11));
        meta.setForeground(BlueSpaceColors.TEXT_MUTED);

        info.add(pwdLabel);
        info.add(meta);

        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        actions.setOpaque(false);

        Button copy = new Button(
                10,
                FontIcon.of(FontAwesomeSolid.COPY, 16, BlueSpaceColors.TEXT_MAIN)
        );
        copy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        copy.setBackground(new Color(0, 0, 0, 0));

        copy.addActionListener((ActionEvent e) -> {
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(fullPwd), null);
            Toast.showSuccess(parent, "Password copied to clipboard", Position.BOTTOM_RIGHT);
        });

        copy.addMouseListener(new MouseAdapter() {

            private HoverTip tip;

            @Override
            public void mouseEntered(MouseEvent e) {
                Window win = SwingUtilities.getWindowAncestor(copy);
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

        actions.add(copy);

        card.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                card.setBackground(BlueSpaceColors.BG_HOVER);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                card.setBackground(BlueSpaceColors.BG_CARD);
            }
        });

        card.add(info, BorderLayout.CENTER);
        card.add(actions, BorderLayout.EAST);

        return card;
    }

    private String shorten(String text, int max) {
        if (text == null || text.length() <= max) {
            return text;
        }
        return text.substring(0, max - 1) + "…";
    }
}
