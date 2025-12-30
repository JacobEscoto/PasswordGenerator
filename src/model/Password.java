package model;

public class Password {

    private String password;
    private String dateCreated;
    private int strengthLevel;

    public Password(String password, String dateCreated, int strengthLevel) {
        this.password = password;
        this.dateCreated = dateCreated;
        this.strengthLevel = strengthLevel;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public int getStrengthLevel() {
        return strengthLevel;
    }

    public void setStrengthLevel(int strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

}
