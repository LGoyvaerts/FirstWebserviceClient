package com.apprentice.ti8m.myfirstrestclient.screens.signup;

/**
 * Created by gol on 31.10.17.
 */

public interface SignUpView {

    void showInvalidEmail();
    void showInvalidPassword();
    void showInvalidConfirmPassword();
    void startMainActivity();
    void setSignedUp();
    void handleFailure();
}
