package com.apprentice.ti8m.myfirstrestclient.validator;

import android.content.SharedPreferences;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.screens.login.LoginPresenter;
import com.apprentice.ti8m.myfirstrestclient.screens.login.LoginPresenterImpl;
import com.apprentice.ti8m.myfirstrestclient.screens.login.LoginView;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import javax.security.auth.callback.Callback;
import javax.security.auth.callback.PasswordCallback;

import okhttp3.Request;
import okhttp3.Response;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by gol on 10.10.17.
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginValidatorTest {

    @Mock
    LoginView loginView;
    @Mock
    APIClient apiClient;
    @Mock
    Callback callback;
    @Mock
    LoginPresenter loginPresenter;
    SharedPreferences sharedPreferences;

    @Before
    public void setUp() {
        loginPresenter = new LoginPresenterImpl(loginView, apiClient);
    }

    @Test
    public void should_return_false_by_wrong_passwords() {
        assertThat(LoginValidator.isBothPasswordsValid("cringe", "cringe")).isFalse();
    }

    @Test
    public void should_validate_email_address() {
        assertThat(LoginValidator.isLoginEmailValid("1@2.3")).isFalse();
        assertThat(LoginValidator.isLoginEmailValid("hans@mueller.com")).isTrue();
        //assertThat(LoginValidator.isLoginValid("lorris@goyvaerts.com", "L2L2L2a%")).isFalse();
    }
}
