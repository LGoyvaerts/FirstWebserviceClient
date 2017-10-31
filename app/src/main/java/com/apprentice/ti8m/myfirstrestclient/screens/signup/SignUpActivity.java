package com.apprentice.ti8m.myfirstrestclient.screens.signup;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.api.APIClient;

public class SignUpActivity extends AppCompatActivity implements SignUpView {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordEditTextConfirm;
    Button signupButton;
    SharedPreferences prefs;
    SignUpPresenter signUpPresenter;

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
        passwordEditText.setError("Email or Password.");
    }

    @Override
    public void showInvalidConfirmPassword() {
        passwordEditTextConfirm.setError("Password not confirmed right.");
    }

}
