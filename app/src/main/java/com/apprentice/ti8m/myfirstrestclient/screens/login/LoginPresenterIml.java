package com.apprentice.ti8m.myfirstrestclient.screens.login;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gol on 30.10.17.
 */

public class LoginPresenterIml implements LoginPresenter {

    private LoginView loginView;
    private APIClient apiClient;

    public LoginPresenterIml(LoginView loginView, APIClient apiClient) {
        this.loginView = loginView;
        this.apiClient = apiClient;
    }

    @Override
    public void login(String email, String password) {
        LoginValidator.isLoginValid(email, password, new ValidatorCallback(this));
    }

    @Override
    public void signUp() {

    }

    @Override
    public void onInvalidPassword() {
        loginView.showInvalidPassword();
    }

    private static class ValidatorCallback implements Callback<Void> {

        private WeakReference<LoginPresenter> loginPresenterWeakReference;

        ValidatorCallback(LoginPresenter loginPresenter) {
            loginPresenterWeakReference = new WeakReference<>(loginPresenter);
        }

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                LoginPresenter activity = loginPresenterWeakReference.get();
                if (activity == null) {
                    return;
                }
                //  prefs = getSharedPreferences("loginRecognizer", MODE_PRIVATE);
                //  SharedPreferences.Editor editor = prefs.edit();
                //  editor.putBoolean("loggedIn", true);
                //  editor.apply();
                //MainActivity.start(activity);
            } else {
                LoginPresenter activity = loginPresenterWeakReference.get();
                if (activity == null) {
                    return;
                }
                activity.onInvalidPassword();
                //LoginActivity activity = loginActivityWeakReference.get();
                //if(activity == null) { return;}
                //activity.passwordEditText.setError("Email or Password is incorrect.");
            }

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            LoginPresenter presenter = loginPresenterWeakReference.get();
            if (presenter == null) {
                return;
            }
            presenter.onInvalidPassword();
        }

    }
}
