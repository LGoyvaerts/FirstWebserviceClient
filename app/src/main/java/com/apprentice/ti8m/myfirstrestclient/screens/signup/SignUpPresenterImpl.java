package com.apprentice.ti8m.myfirstrestclient.screens.signup;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by gol on 31.10.17.
 */

public class SignUpPresenterImpl implements SignUpPresenter {

    private SignUpView signUpView;
    private APIClient apiClient;
    private String email, password, confirmPassword;

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
            LoginValidator.isSignupValid(email, password, confirmPassword, new ValidatorCallback(this, email, password, confirmPassword));
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

    private static class ValidatorCallback implements Callback<Void> {

        private String email, password, confirmPassword;
        private WeakReference<SignUpPresenter> signUpPresenterWeakReference;

        public ValidatorCallback(SignUpPresenter signUpPresenter, String email, String password, String confirmPassword) {
            signUpPresenterWeakReference = new WeakReference<>(signUpPresenter);
            this.email = email;
            this.password = password;
            this.confirmPassword = confirmPassword;
        }

        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                SignUpPresenter presenter = signUpPresenterWeakReference.get();
                if (presenter == null) {
                    return;
                }
                //  prefs = getSharedPreferences("loginRecognizer", MODE_PRIVATE);
                //  SharedPreferences.Editor editor = prefs.edit();
                //  editor.putBoolean("loggedIn", true);
                //  editor.apply();
                // MainActivity.start((Context) presenter);
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
            SignUpPresenter activity = signUpPresenterWeakReference.get();
            if (activity == null) {
                return;
            }
            if (!LoginValidator.isLoginPasswordValid(password)) {
                activity.onInvalidPassword();
            } else if (!LoginValidator.isBothPasswordsValid(password, confirmPassword)) {
                activity.onInvalidConfirmPassword();
            }
            if (!LoginValidator.isLoginEmailValid(email)) {
                activity.onInvalidEmail();
            }
        }

    }
}
