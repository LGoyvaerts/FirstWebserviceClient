package com.apprentice.ti8m.myfirstrestclient.screens.login;

/**
 * Created by gol on 30.10.17.
 */

public interface LoginPresenter {

    void login(String email, String password);
    void signUp();
    void onInvalidPassword();
}
