package com.apprentice.ti8m.myfirstrestclient.screens.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.apprentice.ti8m.myfirstrestclient.R;
import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.screens.MainActivity;
import com.apprentice.ti8m.myfirstrestclient.screens.signup.SignUpActivity;


public class LoginActivity extends AppCompatActivity implements LoginView {

    TextView emailTextView;
    EditText emailEditText;
    EditText passwordEditText;
    RelativeLayout emailIconLayout;
    //TextView wrongCredetials;
    Button loginButton;
    Button signupButton;
    SharedPreferences prefs;
    LoginPresenter loginPresenter;
    TextInputLayout emailEditTextLayout;
    TextInputLayout passwordEditTextLayout;

    public static void start(Context context) {
        Intent starter = new Intent(context, LoginActivity.class);
        context.startActivity(starter);
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        initViews();
        loginPresenter = new LoginPresenterImpl(this, APIClient.getInstance());
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
        emailEditTextLayout = findViewById(R.id.email_input_layout_login);
        passwordEditTextLayout = findViewById(R.id.password_input_layout_login);
        emailIconLayout.bringToFront();
    }

    private void loadData() throws Exception {

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.login(emailEditText.getText().toString(), passwordEditText.getText().toString());
            }
        });

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginPresenter.goToSignUp();
            }
        });
    }

    @Override
    public void showInvalidPassword() {
        passwordEditTextLayout.setErrorEnabled(true);
        passwordEditTextLayout.setError(getString(R.string.login_incorrect_credentials));
    }

    @Override
    public void startMainActivity() {
        MainActivity.start(loginButton.getContext());
    }

    @Override
    public void startSignUpActivity() {
        SignUpActivity.start(signupButton.getContext());
    }
}
