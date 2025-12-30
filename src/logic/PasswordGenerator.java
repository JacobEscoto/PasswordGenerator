package logic;

import model.PasswordConfig;
import java.security.SecureRandom;

public class PasswordGenerator {

    private String getPool(PasswordConfig configuration) {
        String pool = "";
        if (configuration.includesUpper()) {
            pool += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (configuration.includesLower()) {
            pool += "abcdefghijklmnopqrstuvxyz";
        }
        if (configuration.includesNumbers()) {
            pool += "0123456789";
        }
        if (configuration.includesSymbols()) {
            pool += "~!@#$%^&*()_-+={}[]|:;<>,.?";
        }
        return pool;
    }

    public String generatePassword(PasswordConfig configuration) {
        SecureRandom secureRand = new SecureRandom();
        String pool = getPool(configuration);
        String password = "";
        for (int index = 0; index < configuration.getLength(); index++) {
            int randIndex = secureRand.nextInt(0, pool.length());
            password += pool.charAt(randIndex);
        }
        return password;
    }
}
