package com.apprentice.ti8m.myfirstrestclient.validator;

import android.content.SharedPreferences;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by gol on 10.10.17.
 */

public class LoginValidator {


    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    private static Pattern pattern;
    private static Matcher matcher;

    public static Boolean isLoginValid(SharedPreferences sharedPreferences, String email, String password) {
        String uEmail = null;
        String uPassword = null;
        if (isLoginEmailValid(email) && isLoginPasswordValid(password)) {
            if (sharedPreferences.contains("email")) {
                uEmail = sharedPreferences.getString("email", email);
            }
            if (sharedPreferences.contains("password")) {
                uPassword = sharedPreferences.getString("password", password);
            }
        }
        return email.equals(uEmail) && password.equals(uPassword);
    }

    public static Boolean isSignupValid(SharedPreferences sharedPreferences, String email, String password, String confirmPassword) {
        if (isLoginEmailValid(email) && isBothPasswordsValid(password, confirmPassword)) {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();
            return true;
        } else {
            return false;
        }
    }

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
}
