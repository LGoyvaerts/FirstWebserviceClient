package com.apprentice.ti8m.myfirstrestclient.screens.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.screens.MainActivity;
import com.apprentice.ti8m.myfirstrestclient.utils.validator.LoginValidator;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordEditTextConfirm;
    Button signupButton;
    SharedPreferences prefs;
    SignUpPresenter signUpPresenter;
    TextView showPasswordPattern;
    TextInputLayout emailEditTextLayout;
    TextInputLayout passwordEditTextLayout;
    TextInputLayout passwordConfirmtTextLayout;

    public static void start(Context context) {
        Intent starter = new Intent(context, SignUpActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        signUpPresenter = new SignUpPresenterImpl(this, APIClient.getInstance());
        loadData();
    }

    private void initViews() {
        emailEditText = findViewById(R.id.signup_email_edittext);
        passwordEditText = findViewById(R.id.signup_password_edittext_first);
        passwordEditTextConfirm = findViewById(R.id.signup_password_edittext_second);
        signupButton = findViewById(R.id.signup_signup_button);
        showPasswordPattern = findViewById(R.id.signup_show_passwordpattern);
        emailEditTextLayout = findViewById(R.id.email_input_layout_signup);
        passwordEditTextLayout = findViewById(R.id.password_input_layout_signup);
        passwordConfirmtTextLayout = findViewById(R.id.password_confirm_input_layout_signup);

    }

    private void loadData() {
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTextLayoutErrorsDisabled();
                signUpPresenter.signUp(emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        passwordEditTextConfirm.getText().toString());
            }
        });
    }

    @Override
    public void showInvalidEmail() {
        emailEditTextLayout.setErrorEnabled(true);
        emailEditTextLayout.setError(getString(R.string.signup_invalid_email));
    }

    @Override
    public void showInvalidPassword() {
        passwordEditTextLayout.setErrorEnabled(true);
        passwordEditTextLayout.setError(getString(R.string.signup_invalid_password));
    }

    @Override
    public void showInvalidConfirmPassword() {
        passwordConfirmtTextLayout.setErrorEnabled(true);
        passwordConfirmtTextLayout.setError(getString(R.string.signup_invalid_confirm_password));
    }

    @Override
    public void startMainActivity() {
        MainActivity.start(signupButton.getContext());
    }

    @Override
    public void setSignedUp() {
        prefs = getSharedPreferences("loginRecognizer", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("loggedIn", true);
        editor.apply();
    }

    @Override
    public void handleFailure() {
        if (!LoginValidator.isLoginEmailValid(emailEditText.getText().toString())) {
            showInvalidEmail();
        } else if (!LoginValidator.isLoginPasswordValid(passwordEditText.getText().toString())) {
            showInvalidPassword();
            showPasswordPattern.setVisibility(View.VISIBLE);
        } else if (!LoginValidator.isBothPasswordsValid(passwordEditText.getText().toString(), passwordEditTextConfirm.getText().toString())) {
            showInvalidConfirmPassword();
        }
    }

    @Override
    public void userAlreadyExists() {
        emailEditTextLayout.setErrorEnabled(true);
        emailEditTextLayout.setError(getString(R.string.signup_user_already_exists));
    }

    private void setTextLayoutErrorsDisabled() {
        emailEditTextLayout.setErrorEnabled(false);
        passwordEditTextLayout.setErrorEnabled(false);
        passwordConfirmtTextLayout.setErrorEnabled(false);
    }

}
