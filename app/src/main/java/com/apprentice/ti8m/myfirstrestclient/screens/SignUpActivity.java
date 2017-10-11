package com.apprentice.ti8m.myfirstrestclient.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;

public class SignUpActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordEditTextConfirm;
    Button signupButton;
    SharedPreferences sharedPreferences;

    public static void start(Context context) {
        Intent starter = new Intent(context, SignUpActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        initViews();
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
                sharedPreferences = getSharedPreferences("Users", MODE_PRIVATE);
                if (LoginValidator.isSignupValid(sharedPreferences, emailEditText.getText().toString(), passwordEditText.getText().toString(), passwordEditTextConfirm.getText().toString())) {
                    MainActivity.start(SignUpActivity.this);
                }
                if (!LoginValidator.isBothPasswordsValid(passwordEditText.getText().toString(), passwordEditTextConfirm.getText().toString())) {
                    passwordEditTextConfirm.setError("Password not confirmed right.");
                }
                if (!LoginValidator.isLoginEmailValid(emailEditText.getText().toString())) {
                    emailEditText.setError("Email not valid.");
                }
            }
        });
    }
}
