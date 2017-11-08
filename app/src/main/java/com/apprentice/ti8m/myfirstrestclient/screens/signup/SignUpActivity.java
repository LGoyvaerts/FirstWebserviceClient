package com.apprentice.ti8m.myfirstrestclient.screens.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.screens.MainActivity;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordEditTextConfirm;
    Button signupButton;
    SharedPreferences prefs;
    SignUpPresenter signUpPresenter;
    TextView showPasswordPattern;

    public static void start(Context context) {
        Intent starter = new Intent(context, SignUpActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
        signUpPresenter = new SignUpPresenterImpl(this, new APIClient());
        loadData();
    }

    private void initViews() {
        emailEditText = findViewById(R.id.signup_email_edittext);
        passwordEditText = findViewById(R.id.signup_password_edittext_first);
        passwordEditTextConfirm = findViewById(R.id.signup_password_edittext_second);
        signupButton = findViewById(R.id.signup_signup_button);
        showPasswordPattern = findViewById(R.id.signup_show_passwordpattern);

    }

    private void loadData() {
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpPresenter.signUp(emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        passwordEditTextConfirm.getText().toString());
            }
        });
    }

    @Override
    public void showInvalidEmail() {
        emailEditText.setError("Email is not valid.");
    }

    @Override
    public void showInvalidPassword() {
        passwordEditText.setError("Password not valid.");
    }

    @Override
    public void showInvalidConfirmPassword() {
        passwordEditTextConfirm.setError("Password not confirmed right.");
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
        if (!LoginValidator.isLoginPasswordValid(passwordEditText.getText().toString())) {
            showInvalidPassword();
            showPasswordPattern.setVisibility(View.VISIBLE);
        } else if (!LoginValidator.isBothPasswordsValid(passwordEditText.getText().toString(), passwordEditTextConfirm.getText().toString())) {
            showInvalidConfirmPassword();
        }
        if (!LoginValidator.isLoginEmailValid(emailEditText.getText().toString())) {
            showInvalidEmail();
        }
    }

}
