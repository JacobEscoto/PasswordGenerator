package logic;

public class StrengthAnalyzer {

    public enum StrengthLevel {
        WEAK("Weak"), MEDIUM("Medium"), STRONG("Strong"), VERY_STRONG("Very Strong");
        
        private final String description;
        
        private StrengthLevel(String description) {
            this.description = description;
        }
        
        public String getDescription() {
            return description;
        }
    }

    public StrengthLevel checkPasswordStrength(String password) {
        int points = getPointsByLength(password.length()) + getPointsByVariety(password) - penalizePatterns(password);
        if (points < 0) {
            points = 0;
        } else if (points > 100) {
            points = 100;
        }
        return convertPointsToLevel(points);
    }

    private int getPointsByLength(int length) {
        if (length < 8) {
            return 0;
        } else if (length >= 8 && length <= 11) {
            return 10;
        } else if (length >= 12 && length <= 15) {
            return 20;
        } else {
            return 30;
        }
    }

    private int getPointsByVariety(String password) {
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasNumbers = false;
        boolean hasSymbols = false;
        for (int index = 0; index < password.length(); index++) {
            char character = password.charAt(index);
            if (Character.isUpperCase(character)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(character)) {
                hasLowerCase = true;
            } else if (Character.isDigit(character)) {
                hasNumbers = true;
            } else {
                hasSymbols = true;
            }
        }
        boolean hasAll = hasUpperCase && hasLowerCase && hasNumbers && hasSymbols;
        int totalPoints = 0;
        if (hasUpperCase) {
            totalPoints += 8;
        }
        if (hasLowerCase) {
            totalPoints += 8;
        }
        if (hasNumbers) {
            totalPoints += 8;
        }
        if (hasSymbols) {
            totalPoints += 10;
        }
        if (hasAll) {
            totalPoints += 5;
        }
        return totalPoints;
    }

    private int penalizePatterns(String password) {
        int penalty = 0;
        if (password.length() > 3) {
            for (int index = 0; index < password.length() - 2; index++) { // detect sequence of numbers
                char first = password.charAt(index);
                char second = password.charAt(index + 1);
                char third = password.charAt(index + 2);
                if (Character.isDigit(first) && Character.isDigit(second) && Character.isDigit(third)) {
                    int firstNumber = Character.getNumericValue(first);
                    int secondNumber = Character.getNumericValue(second);
                    int thirdNumber = Character.getNumericValue(third);

                    if (secondNumber == firstNumber + 1 && thirdNumber == secondNumber + 1) {
                        penalty += 10;
                    }
                }
            }

            for (int index = 0; index < password.length() - 2; index++) { // detect alphabetical sequence
                char first = password.charAt(index);
                char second = password.charAt(index + 1);
                char third = password.charAt(index + 2);

                if (Character.isLetter(first) && Character.isLetter(second) && Character.isLetter(third)) {
                    char lower1 = Character.toLowerCase(first);
                    char lower2 = Character.toLowerCase(second);
                    char lower3 = Character.toLowerCase(third);

                    if (lower2 == lower1 + 1 && lower3 == lower2 + 1) {
                        penalty += 10;
                    }
                }
            }

            for (int index = 0; index < password.length() - 2; index++) { // detect repeated characters
                char first = password.charAt(index);
                char second = password.charAt(index + 1);
                char third = password.charAt(index + 2);

                if (first == second && second == third) {
                    penalty += 10;
                }
            }

            penalty += (hasCommonWords(password) ? 15 : 0) + (hasKeyboardSequence(password) ? 10 : 0);
        }

        return penalty;
    }

    private boolean hasCommonWords(String password) {
        String passwordLower = password.toLowerCase();
        String[] commonWords = {
            "password", "admin", "admin123", "letmein", "welcome",
            "monkey", "dragon", "master", "hello",
            "login", "pass", "user123", "password_admin"
        };

        for (String word : commonWords) {
            if (passwordLower.contains(word)) {
                return true;
            }
        }
        return false;
    }
    
    private boolean hasKeyboardSequence(String password) {
        String passwordLower = password.toLowerCase();
        
        String[] keyboardPatterns = {
            "qwerty", "asdf", "zxcv", "qwertyuiop", "asdfghjkl", "zxcvbnm", "12345",
            "54321"
        };
        for (String pattern : keyboardPatterns) {
            if (passwordLower.contains(pattern)) {
                return true;
            }
        }
        return false;
    }

    private StrengthLevel convertPointsToLevel(int score) {
        if (score <= 25) {
            return StrengthLevel.WEAK;
        } else if (score <= 50) {
            return StrengthLevel.MEDIUM;
        } else if (score <= 75) {
            return StrengthLevel.STRONG;
        } else {
            return StrengthLevel.VERY_STRONG;
        }
    }
}
