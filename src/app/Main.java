package app;

import logic.PasswordGenerator;
import model.PasswordConfig;

public class Main {

    public static void main(String[] args) {
        PasswordConfig config = new PasswordConfig(17, true, true, false, true);
        PasswordGenerator gen = new PasswordGenerator();
        String password = gen.generatePassword(config);
        System.out.println(password);
        
    }

}
