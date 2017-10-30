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
import com.apprentice.ti8m.myfirstrestclient.model.User;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpActivity extends AppCompatActivity {

    EditText emailEditText;
    EditText passwordEditText;
    EditText passwordEditTextConfirm;
    Button signupButton;
    SharedPreferences prefs;

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
                LoginValidator.isSignupValid(emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        passwordEditTextConfirm.getText().toString(),
                        new ValidatorCallback(SignUpActivity.this));
                if (!LoginValidator.isLoginPasswordValid(passwordEditText.getText().toString())) {
                    passwordEditText.setError("Password is not valid.");
                } else if (!LoginValidator.isBothPasswordsValid(passwordEditText.getText().toString(), passwordEditTextConfirm.getText().toString())) {
                    passwordEditTextConfirm.setError("Password not confirmed right.");
                }
                if (!LoginValidator.isLoginEmailValid(emailEditText.getText().toString())) {
                    emailEditText.setError("Email is not valid.");
                }
            }
        });
    }

    private class ValidatorCallback implements Callback<Void> {

        private WeakReference<SignUpActivity> signUpActivityWeakReference;
        public ValidatorCallback(SignUpActivity loginActivity){
            signUpActivityWeakReference = new WeakReference<>(loginActivity);
        }
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                SignUpActivity activity = signUpActivityWeakReference.get();
                if(activity == null) { return;}
                prefs = getSharedPreferences("loginRecognizer", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loggedIn", true);
                editor.apply();
                MainActivity.start(activity);
            } else {
                SignUpActivity activity = signUpActivityWeakReference.get();
                if(activity == null) { return;}
                activity.passwordEditText.setError("Email or Password is incorrect.");
            }

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            SignUpActivity activity = signUpActivityWeakReference.get();
            if(activity == null) { return;}
            //TODO error meldung für user
        }

    }
}
