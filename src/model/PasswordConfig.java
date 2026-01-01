package model;

public class PasswordConfig {

    private int length;
    private boolean includesUpper;
    private boolean includesLower;
    private boolean includesNumbers;
    private boolean includesSymbols;

    public PasswordConfig() {}

    public PasswordConfig(int length, boolean includesUpper, boolean includesLower, boolean includesNumbers, boolean includesSymbols) {
        this.length = length;
        this.includesUpper = includesUpper;
        this.includesLower = includesLower;
        this.includesNumbers = includesNumbers;
        this.includesSymbols = includesSymbols;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public boolean includesUpper() {
        return includesUpper;
    }

    public void setIncludesUpper(boolean includesUpper) {
        this.includesUpper = includesUpper;
    }

    public boolean includesLower() {
        return includesLower;
    }

    public void setIncludesLower(boolean includesLower) {
        this.includesLower = includesLower;
    }

    public boolean includesNumbers() {
        return includesNumbers;
    }

    public void setIncludesNumbers(boolean includesNumbers) {
        this.includesNumbers = includesNumbers;
    }

    public boolean includesSymbols() {
        return includesSymbols;
    }

    public void setIncludesSymbols(boolean includesSymbols) {
        this.includesSymbols = includesSymbols;
    }

}
