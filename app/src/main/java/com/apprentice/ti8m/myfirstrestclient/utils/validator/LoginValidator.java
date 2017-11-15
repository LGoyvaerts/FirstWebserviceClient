package com.apprentice.ti8m.myfirstrestclient.utils.validator;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by gol on 10.10.17.
 * Don't copy my Stuff!
 */

public class LoginValidator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    private static Pattern pattern;
    private static Matcher matcher;

    public static Boolean isLoginEmailValid(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isLoginPasswordValid(String password) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return (matcher.matches());
    }

    public static Boolean isBothPasswordsValid(String password, String confirmPasword) {
        return isLoginPasswordValid(password) && password.equals(confirmPasword);
    }

    public static String encryptStringToSHA256(String toEncrypt) {
        String encrypted = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(toEncrypt.getBytes("UTF-8"));
            byte[] encrptedHash = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte anEncrptedHash : encrptedHash) {
                sb.append(Integer.toHexString(0xFF & anEncrptedHash));
            }
            encrypted = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encrypted;
    }

}
