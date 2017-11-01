package com.apprentice.ti8m.myfirstrestclient.screens.login;

import android.content.Context;

import com.apprentice.ti8m.myfirstrestclient.screens.MainActivity;

/**
 * Created by gol on 30.10.17.
 */

public interface LoginView {

    void showInvalidPassword();
    void startMainActivity();
    void startSignUpActivity();

}
