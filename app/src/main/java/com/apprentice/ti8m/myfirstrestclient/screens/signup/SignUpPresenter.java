package com.apprentice.ti8m.myfirstrestclient.screens.signup;

import android.content.Context;

/**
 * Created by gol on 31.10.17.
 */

public interface SignUpPresenter {

    void signUp(String email, String password, String confirmPassword);
    void onInvalidEmail();
    void onInvalidPassword();
    void onInvalidConfirmPassword();
    void onStartMainActivity();
    void onSetSignedUp();
    void onHandleFailure();
    void onUserAlreadyExists();
}
