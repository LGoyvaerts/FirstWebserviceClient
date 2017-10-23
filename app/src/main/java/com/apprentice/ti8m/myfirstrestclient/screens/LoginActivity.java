package com.apprentice.ti8m.myfirstrestclient.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.validator.LoginValidator;


public class LoginActivity extends AppCompatActivity {

    TextView emailTextView;
    EditText emailEditText;
    EditText passwordEditText;
    //TextView wrongCredetials;
    Button loginButton;
    Button signupButton;
    SharedPreferences sharedPreferences;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        sharedPreferences = getApplicationContext().getSharedPreferences("Users", 0);
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
        //wrongCredetials = findViewById(R.id.wrong_credentials_textview);
        loginButton = findViewById(R.id.login_login_button);
        signupButton = findViewById(R.id.login_signup_button);
    }

    private void loadData() throws Exception {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (LoginValidator.isLoginValid(sharedPreferences, emailEditText.getText().toString(), passwordEditText.getText().toString())) {
                    MainActivity.start(LoginActivity.this);
                } else passwordEditText.setError("Email or Password is incorrect.");
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUpActivity.start(LoginActivity.this);
            }
        });
    }
}
