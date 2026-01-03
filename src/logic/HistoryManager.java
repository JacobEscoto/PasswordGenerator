package logic;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import model.Password;

public class HistoryManager {

    private static final int MAX = 20;
    private Deque<Password> history;

    public HistoryManager() {
        history = new ArrayDeque();
    }

    public synchronized void addPassword(Password password) {
        if (password == null) {
            return;
        }
        if (history.size() == MAX) {
            history.removeFirst();
        }
        history.addLast(password);
    }

    public synchronized List<Password> getHistory() {
        return new ArrayList(history);
    }

    public synchronized void clearHistory() {
        history.clear();
    }
}
