package com.apprentice.ti8m.myfirstrestclient.screens.login;

import android.view.View;

/**
 * Created by gol on 30.10.17.
 */

public interface LoginPresenter {

    void login(String email, String password);
    void goToSignUp();
    void onInvalidPassword();
    void onStartActivityMain();
    void onStartSignUpActivity();
}
