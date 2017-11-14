package com.apprentice.ti8m.myfirstrestclient.screens.login;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.model.User;
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
    private ValidatorCallback validatorCallback;

    public LoginPresenterImpl(LoginView loginView, APIClient apiClient) {
        this.loginView = loginView;
        this.apiClient = apiClient;
        this.validatorCallback = new ValidatorCallback(this);
    }

    @Override
    public void login(String email, String password) {
        String encryptedPassword = LoginValidator.encryptStringToSHA256(password);
        apiClient.getUser(new User(email, encryptedPassword))
                 .enqueue(validatorCallback);
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
                LoginPresenter loginPresenter = loginPresenterWeakReference.get();
                if (loginPresenter == null) {
                    return;
                }
                loginPresenter.onStartActivityMain();

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
