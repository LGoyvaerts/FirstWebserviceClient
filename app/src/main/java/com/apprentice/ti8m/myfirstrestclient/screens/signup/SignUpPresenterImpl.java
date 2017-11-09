package com.apprentice.ti8m.myfirstrestclient.screens.signup;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gol on 31.10.17.
 * Don't copy my Stuff!
 */

public class SignUpPresenterImpl implements SignUpPresenter {

    private SignUpView signUpView;
    private APIClient apiClient;

    public SignUpPresenterImpl(SignUpView signUpView, APIClient apiClient) {
        this.signUpView = signUpView;
        this.apiClient = apiClient;
    }


    @Override
    public void signUp(String email, String password, String confirmPassword) {
        boolean temp = false;
        if (!LoginValidator.isLoginEmailValid(email)) {
            onInvalidEmail();
            temp = true;
        }
        if (!LoginValidator.isBothPasswordsValid(password, confirmPassword)) {
            onInvalidPassword();
            onInvalidConfirmPassword();
            temp = true;
        }
        if (!temp) {
            LoginValidator.isSignupValid(email, password, confirmPassword, new ValidatorCallback(this));
        }
    }

    @Override
    public void onInvalidEmail() {
        signUpView.showInvalidEmail();
    }

    @Override
    public void onInvalidPassword() {
        signUpView.showInvalidPassword();
    }

    @Override
    public void onInvalidConfirmPassword() {
        signUpView.showInvalidConfirmPassword();
    }

    @Override
    public void onStartMainActivity() {
        signUpView.startMainActivity();
    }

    @Override
    public void onSetSignedUp() {
        signUpView.setSignedUp();
    }

    @Override
    public void onHandleFailure() {
        signUpView.handleFailure();
    }


    private static class ValidatorCallback implements Callback<Void> {

        private WeakReference<SignUpPresenter> signUpPresenterWeakReference;

        ValidatorCallback(SignUpPresenter signUpPresenter) {
            signUpPresenterWeakReference = new WeakReference<>(signUpPresenter);
        }

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                SignUpPresenter presenter = signUpPresenterWeakReference.get();
                if (presenter == null) {
                    return;
                }
                presenter.onSetSignedUp();
                presenter.onStartMainActivity();

            } else {
                SignUpPresenter signUpPresenter = signUpPresenterWeakReference.get();
                if (signUpPresenter == null) {
                    return;
                }
                signUpPresenter.onInvalidPassword();
            }

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            SignUpPresenter signUpPresenter = signUpPresenterWeakReference.get();
            if (signUpPresenter == null) {
                return;
            }
            signUpPresenter.onHandleFailure();
        }

    }
}
