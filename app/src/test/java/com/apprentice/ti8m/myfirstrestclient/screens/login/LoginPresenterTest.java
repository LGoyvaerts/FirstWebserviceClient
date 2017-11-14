package com.apprentice.ti8m.myfirstrestclient.screens.login;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.api.APIInterface;
import com.apprentice.ti8m.myfirstrestclient.model.User;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.io.IOException;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.mockito.ArgumentMatchers.any;

/**
 * Created by gol on 13.11.17.
 * Don't copy my Stuff!
 */

@RunWith(MockitoJUnitRunner.class)
public class LoginPresenterTest {

    @Mock
    LoginView loginViewMock;
    @Mock
    APIClient apiClientMock;

    private LoginPresenter loginPresenter;

    @Before
    public void setUp() {
        loginPresenter = new LoginPresenterImpl(loginViewMock, apiClientMock);

    }

    @Test
    public void should_show_invalid_password() {
        loginPresenter.onInvalidPassword();
        Mockito.verify(loginViewMock).showInvalidPassword();

    }

    @Test
    public void should_return_error_by_invalid_credentials(){
        Mockito.when(apiClientMock.getUser(any(User.class))).thenReturn(new Call<Void>() {
            @Override
            public Response<Void> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<Void> callback) {
                callback.onFailure(null,null);
            }

            @Override
            public boolean isExecuted() {
                return false;
            }

            @Override
            public void cancel() {

            }

            @Override
            public boolean isCanceled() {
                return false;
            }

            @Override
            public Call<Void> clone() {
                return null;
            }

            @Override
            public Request request() {
                return null;
            }
        });
        loginPresenter.login("aaa", "bbb");
        Mockito.verify(loginViewMock).showInvalidPassword();
    }

    @Test
    public void should_start_MainActivity() {
        loginPresenter.onStartActivityMain();
        Mockito.verify(loginViewMock).startMainActivity();
    }

    @Test
    public void should_start_SignUpActivity() {
        loginPresenter.onStartSignUpActivity();
        Mockito.verify(loginViewMock).startSignUpActivity();
    }
}
