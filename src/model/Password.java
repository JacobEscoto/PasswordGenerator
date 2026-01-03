package model;

public class Password {

    private String password;
    private String dateCreated;
    private String strengthLevel;

    public Password(String password, String dateCreated, String strengthLevel) {
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

    public String getStrengthLevel() {
        return strengthLevel;
    }

    public void setStrengthLevel(String strengthLevel) {
        this.strengthLevel = strengthLevel;
    }

}
