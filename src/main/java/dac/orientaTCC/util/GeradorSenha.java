package dac.orientaTCC.util;

import java.security.SecureRandom;

public class GeradorSenha {

    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String DIGITS = "0123456789";
    private static final String SPECIAL_CHARS = "!@#$%&*()-_=+<>?";

    private static final String PASSWORD_ALLOW_BASE = CHAR_LOWER + CHAR_UPPER + DIGITS + SPECIAL_CHARS;
    private static final SecureRandom random = new SecureRandom();

    public static String gerarSenhaAleatoria() {
        StringBuilder sb = new StringBuilder(6);

        for (int i = 0; i < 6; i++) {
            int rndCharAt = random.nextInt(PASSWORD_ALLOW_BASE.length());
            char rndChar = PASSWORD_ALLOW_BASE.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }
}