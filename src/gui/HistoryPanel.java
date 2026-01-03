package gui;

import gui.components.Button;
import gui.utils.HoverTip;
import gui.utils.Toast;
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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import model.Password;
import org.kordamp.ikonli.fontawesome5.FontAwesomeSolid;
import org.kordamp.ikonli.swing.FontIcon;

public class HistoryPanel extends JPanel {

    private final JPanel listContainer;
    private final Button clearBtn;
    private final MainFrame parent;

    public HistoryPanel(MainFrame parent) {
        this.parent = parent;
        setLayout(new BorderLayout());
        setOpaque(true);
        setBackground(gui.utils.BlueSpaceColors.BG_MAIN);
        setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        // === HEADER ===
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        header.setOpaque(false);
        JLabel title = new JLabel("History");
        title.setFont(new Font("SansSerif", Font.BOLD, 16));
        title.setForeground(gui.utils.BlueSpaceColors.TEXT_MAIN);
        title.setHorizontalAlignment(SwingConstants.LEFT);

        clearBtn = new Button(10, FontIcon.of(FontAwesomeSolid.TRASH_ALT, 16, gui.utils.BlueSpaceColors.RED));
        clearBtn.setBackground(new Color(0, 0, 0, 0));
        clearBtn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        clearBtn.addActionListener(e -> {
            parent.getHistoryManager().clearHistory();
            refresh(parent.getHistoryManager().getHistory());
        });

        clearBtn.addMouseListener(new MouseAdapter() {
            private HoverTip tip;
            @Override
            public void mouseEntered(MouseEvent e) {
                Window win = SwingUtilities.getWindowAncestor(clearBtn);
                tip = new HoverTip(win, "Clear history");
                Point p = e.getLocationOnScreen();
                tip.showAt(new Point(p.x, p.y + 24));
            }
            @Override
            public void mouseExited(MouseEvent e) {
                if (tip != null) tip.hideTip();
            }
        });

        header.add(title);
        header.add(Box.createHorizontalGlue());
        header.add(clearBtn);
        add(header, BorderLayout.NORTH);

        // === LIST CONTAINER ===
        listContainer = new JPanel();
        listContainer.setLayout(new BoxLayout(listContainer, BoxLayout.Y_AXIS));
        listContainer.setOpaque(false);

        JScrollPane scroll = new JScrollPane(listContainer);
        scroll.setPreferredSize(new Dimension(320, 300));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.setOpaque(false);
        scroll.getViewport().setOpaque(false);
        scroll.getViewport().setBackground(gui.utils.BlueSpaceColors.BG_MAIN);

        add(scroll, BorderLayout.CENTER);
    }

    public void refresh(List<Password> history) {
        listContainer.removeAll();
        if (history == null || history.isEmpty()) {
            JLabel empty = new JLabel("No passwords yet");
            empty.setForeground(gui.utils.BlueSpaceColors.TEXT_MUTED);
            empty.setBorder(BorderFactory.createEmptyBorder(12, 6, 12, 6));
            listContainer.add(empty);
        } else {
            for (int i = history.size() - 1; i >= 0; i--) {
                Password p = history.get(i);
                listContainer.add(createRow(p));
                listContainer.add(Box.createVerticalStrut(8));
            }
        }
        listContainer.revalidate();
        listContainer.repaint();
    }

    private JPanel createRow(Password p) {
        JPanel row = new JPanel(new BorderLayout(8, 0));
        row.setOpaque(true);
        row.setBackground(gui.utils.BlueSpaceColors.BG_CARD);
        row.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(gui.utils.BlueSpaceColors.BORDER),
                BorderFactory.createEmptyBorder(6, 8, 6, 8)
        ));

        // --- Info Labels ---
        JPanel info = new JPanel(new GridLayout(2, 1));
        info.setOpaque(false);
        JLabel pwdLabel = new JLabel(p.getPassword());
        pwdLabel.setFont(pwdLabel.getFont().deriveFont(13f));
        pwdLabel.setForeground(gui.utils.BlueSpaceColors.TEXT_MAIN);
        JLabel dateLabel = new JLabel(p.getDateCreated() + " • " + p.getStrengthLevel());
        dateLabel.setFont(dateLabel.getFont().deriveFont(11f));
        dateLabel.setForeground(gui.utils.BlueSpaceColors.TEXT_MUTED);
        info.add(pwdLabel);
        info.add(dateLabel);

        // --- Actions ---
        JPanel actions = new JPanel(new FlowLayout(FlowLayout.RIGHT, 6, 0));
        actions.setOpaque(false);
        Button copy = new Button(10, FontIcon.of(FontAwesomeSolid.COPY, 16, gui.utils.BlueSpaceColors.TEXT_MAIN));
        copy.setBackground(new Color(0, 0, 0, 0));
        copy.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        copy.addActionListener((ActionEvent e) -> {
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(new StringSelection(p.getPassword()), null);
            Toast.showSuccess(parent, "Password copied to clipboard");
        });
        actions.add(copy);

        row.add(info, BorderLayout.CENTER);
        row.add(actions, BorderLayout.EAST);

        // --- Hover effect ---
        row.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                row.setBackground(gui.utils.BlueSpaceColors.BG_HOVER);
            }
            @Override
            public void mouseExited(MouseEvent e) {
                row.setBackground(gui.utils.BlueSpaceColors.BG_CARD);
            }
        });

        row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 48));
        return row;
    }
}
