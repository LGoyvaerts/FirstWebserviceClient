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

public class LoginPresenterImpl implements LoginPresenter {

    private LoginView loginView;
    private APIClient apiClient;

    public LoginPresenterImpl(LoginView loginView, APIClient apiClient) {
        this.loginView = loginView;
        this.apiClient = apiClient;
    }

    @Override
    public void login(String email, String password) {
        LoginValidator.isLoginValid(email, password, new ValidatorCallback(this));
    }

    @Override
    public void goToSignUp() {
        onStartSignUpActivity();
    }

    @Override
    public void onInvalidPassword() {
        loginView.showInvalidPassword();
    }

    @Override
    public void onStartActivityMain() {
        loginView.startMainActivity();
    }

    @Override
    public void onStartSignUpActivity() {
        loginView.startSignUpActivity();
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
                activity.onStartActivityMain();

            } else {
                LoginPresenter activity = loginPresenterWeakReference.get();
                if (activity == null) {
                    return;
                }
                activity.onInvalidPassword();
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
