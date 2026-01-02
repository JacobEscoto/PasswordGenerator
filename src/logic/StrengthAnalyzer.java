package logic;

public class StrengthAnalyzer {

    public static class StrengthResult {

        private final StrengthLevel level;
        private final int score;

        public StrengthResult(StrengthLevel level, int score) {
            this.level = level;
            this.score = score;
        }

        public StrengthLevel getLevel() {
            return level;
        }

        public int getScore() {
            return score;
        }
    }

    public enum StrengthLevel {
        WEAK("Weak"),
        MEDIUM("Medium"),
        STRONG("Strong"),
        VERY_STRONG("Very Strong");

        private final String description;

        StrengthLevel(String description) {
            this.description = description;
        }

        public String getDescription() {
            return description;
        }
    }

    public StrengthResult checkPasswordStrength(String password) {
        if (password == null || password.isEmpty()) {
            return new StrengthResult(StrengthLevel.WEAK, 0);
        }

        int points = getPointsByLength(password.length()) + getPointsByVariety(password) - penalizePatterns(password);

        int maxScore = 69;
        points = (points * 100) / maxScore;

        StrengthLevel level = convertPointsToLevel(points);
        return new StrengthResult(level, points);
    }

    private int getPointsByLength(int length) {
        if (length < 8) {
            return 0;
        }
        if (length <= 11) {
            return 15;
        }
        if (length <= 15) {
            return 25;
        }
        return 40;
    }

    private int getPointsByVariety(String password) {
        boolean upper = false;
        boolean lower = false;
        boolean digit = false;
        boolean symbol = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                upper = true;
            } else if (Character.isLowerCase(c)) {
                lower = true;
            } else if (Character.isDigit(c)) {
                digit = true;
            } else {
                symbol = true;
            }
        }

        int points = 0;
        if (upper) {
            points += 10;
        }
        if (lower) {
            points += 10;
        }
        if (digit) {
            points += 10;
        }
        if (symbol) {
            points += 10;
        }

        if (upper && lower && digit && symbol) {
            points += 10;
        }

        return points;
    }

    private int penalizePatterns(String password) {
        int penalty = 0;

        if (hasCommonWords(password)) {
            penalty = Math.max(penalty, 25);
        }

        if (hasKeyboardSequence(password)) {
            penalty = Math.max(penalty, 15);
        }

        if (hasLongSequentialChars(password, 4)) {
            penalty = Math.max(penalty, 15);
        }

        if (hasDominantRepetition(password, password.length() / 2)) {
            penalty = Math.max(penalty, 15);
        }

        return penalty;
    }

    private boolean hasLongSequentialChars(String password, int minLength) {
        if (password.length() < minLength) {
            return false;
        }

        int count = 1;

        for (int i = 1; i < password.length(); i++) {
            char prev = password.charAt(i - 1);
            char curr = password.charAt(i);

            // Letras
            if (Character.isLetter(prev) && Character.isLetter(curr)) {
                if (Character.toLowerCase(curr) == Character.toLowerCase(prev) + 1) {
                    count++;
                } else {
                    count = 1;
                }
            } // Números
            else if (Character.isDigit(prev) && Character.isDigit(curr)) {
                if (curr == prev + 1) {
                    count++;
                } else {
                    count = 1;
                }
            } else {
                count = 1;
            }

            if (count >= minLength) {
                return true;
            }
        }

        return false;
    }

    private boolean hasDominantRepetition(String password, int threshold) {
        if (password == null || password.isEmpty()) {
            return false;
        }

        int[] freq = new int[128];
        int max = 0;

        for (char c : password.toCharArray()) {
            if (c < 128) {
                freq[c]++;
                max = Math.max(max, freq[c]);
            }
        }

        return max >= threshold;
    }

    private boolean hasCommonWords(String password) {
        String p = password.toLowerCase();
        String[] common = {
            "password", "admin", "admin123", "letmein",
            "welcome", "monkey", "dragon", "login", "qwerty"
        };
        for (String w : common) {
            if (p.contains(w)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasKeyboardSequence(String password) {
        String p = password.toLowerCase();
        String[] patterns = {
            "qwerty", "asdf", "zxcv", "12345", "54321"
        };
        for (String pat : patterns) {
            if (p.contains(pat)) {
                return true;
            }
        }
        return false;
    }

    private StrengthLevel convertPointsToLevel(int score) {
        if (score <= 25) {
            return StrengthLevel.WEAK;
        }
        if (score <= 50) {
            return StrengthLevel.MEDIUM;
        }
        if (score <= 75) {
            return StrengthLevel.STRONG;
        }
        return StrengthLevel.VERY_STRONG;
    }
}
