package com.apprentice.ti8m.myfirstrestclient.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.model.User;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;

import java.lang.ref.WeakReference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity {

    TextView emailTextView;
    EditText emailEditText;
    EditText passwordEditText;
    RelativeLayout emailIconLayout;
    //TextView wrongCredetials;
    Button loginButton;
    Button signupButton;
    private ValidatorCallback validatorCallback = new ValidatorCallback(this);
    SharedPreferences prefs;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        try {
            loadData();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        //fragment = findViewById(R.id.fragment);
        emailTextView = findViewById(R.id.login_email_textview);
        emailEditText = findViewById(R.id.login_email_edittext);
        passwordEditText = findViewById(R.id.login_password_edittext);
        emailIconLayout = findViewById(R.id.login_email_icon_layout);
        //wrongCredetials = findViewById(R.id.wrong_credentials_textview);
        loginButton = findViewById(R.id.login_login_button);
        signupButton = findViewById(R.id.login_signup_button);
        emailIconLayout.bringToFront();
    }

    private void loadData() throws Exception {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LoginValidator.isLoginValid( emailEditText.getText().toString(),
                        passwordEditText.getText().toString(),
                        validatorCallback);
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.start(LoginActivity.this);
            }
        });
    }

    private class ValidatorCallback implements Callback<Void> {

        private WeakReference<LoginActivity> loginActivityWeakReference;
        public ValidatorCallback(LoginActivity loginActivity){
            loginActivityWeakReference = new WeakReference<>(loginActivity);
        }
        @Override
        public void onResponse(Call<Void> call, Response<Void> response) {
            if (response.isSuccessful()) {
                LoginActivity activity = loginActivityWeakReference.get();
                if(activity == null) { return;}
                prefs = getSharedPreferences("loginRecognizer", MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putBoolean("loggedIn", true);
                editor.apply();
                MainActivity.start(activity);
            } else {
                LoginActivity activity = loginActivityWeakReference.get();
                if(activity == null) { return;}
                activity.passwordEditText.setError("Email or Password is incorrect.");
            }

        }

        @Override
        public void onFailure(Call<Void> call, Throwable t) {
            LoginActivity activity = loginActivityWeakReference.get();
            if(activity == null) { return;}
           //TODO error meldung für user
        }

    }
}
