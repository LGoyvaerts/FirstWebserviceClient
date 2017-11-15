package com.apprentice.ti8m.myfirstrestclient.screens.signup;

import com.apprentice.ti8m.myfirstrestclient.api.APIClient;
import com.apprentice.ti8m.myfirstrestclient.model.User;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

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
public class SignUpPresenterTest {

    @Mock
    SignUpView signUpViewMock;
    @Mock
    APIClient apiClientMock;
    private SignUpPresenter signUpPresenter;

    @Before
    public void setUp() {
        signUpPresenter = new SignUpPresenterImpl(signUpViewMock, apiClientMock);

        Mockito.when(apiClientMock.createUser(any(User.class))).thenReturn(new Call<Void>() {
            @Override
            public Response<Void> execute() throws IOException {
                return null;
            }

            @Override
            public void enqueue(Callback<Void> callback) {
                callback.onFailure(null, null);
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
    }

    @Test
    public void should_show_invalid_email() {
        signUpPresenter.onInvalidEmail();
        Mockito.verify(signUpViewMock).showInvalidEmail();
    }

    @Test
    public void should_show_invalid_password() {
        signUpPresenter.onInvalidPassword();
        Mockito.verify(signUpViewMock).showInvalidPassword();
    }

    @Test
    public void should_show_invalid_confirm_password() {
        signUpPresenter.onInvalidConfirmPassword();
        Mockito.verify(signUpViewMock).showInvalidConfirmPassword();
    }

    @Test
    public void should_start_MainActivity(){
        signUpPresenter.onStartMainActivity();
        Mockito.verify(signUpViewMock).startMainActivity();
    }

    @Test
    public void should_set_signed_up(){
        signUpPresenter.onSetSignedUp();
        Mockito.verify(signUpViewMock).setSignedUp();
    }

    @Test
    public void should_handle_failure(){
        signUpPresenter.onHandleFailure();
        Mockito.verify(signUpViewMock).handleFailure();
    }

    @Test
    public void should_fail_signup_by_non_valid_credentials(){
        signUpPresenter.signUp("dighe", "somepassword", "somepasswofd");
        Mockito.verify(signUpViewMock).showInvalidEmail();
        Mockito.verify(signUpViewMock).showInvalidPassword();
        Mockito.verify(signUpViewMock).showInvalidConfirmPassword();
    }

    @Test
    public void should_fail_signup_by_existing_user(){
        signUpPresenter.onUserAlreadyExists();
        Mockito.verify(signUpViewMock).userAlreadyExists();
    }


    //All Tests with Response from Server beginning with --> _

    @Test
    public void _should_fail_signup_by_existing_user(){
        signUpPresenter.signUp("ttttest@test.ch", "Test1234_", "Test1234_");
        Mockito.verify(signUpViewMock).handleFailure();
    }

    @Test
    public void _should_fail_signup_by_invalid_email(){
        signUpPresenter.signUp("aaa@aa.a", "Test1234_", "Test1234_");
        Mockito.verify(signUpViewMock).showInvalidEmail();
    }

    @Test
    public void _should_fail_signup_by_invalid_password(){
        signUpPresenter.signUp("testmail@test.ch", "password", "password");
        Mockito.verify(signUpViewMock).showInvalidPassword();
    }

    @Test
    public void should_fail_signup_by_wrong_confirm_password(){
        signUpPresenter.signUp("ttttest@test.ch", "Test1234_1", "Test1234_");
        Mockito.verify(signUpViewMock).showInvalidConfirmPassword();
    }

    @After
    public void cleanUp(){
        Mockito.reset(apiClientMock);
    }
}
