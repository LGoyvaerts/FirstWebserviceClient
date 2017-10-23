package com.apprentice.ti8m.myfirstrestclient.validator;

import android.content.SharedPreferences;

import org.junit.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Created by gol on 10.10.17.
 */

public class LoginValidatorTest {

    SharedPreferences sharedPreferences;

    @Test
    public void should_return_false_by_wrong_password() {
        assertThat(LoginValidator.isLoginValid(sharedPreferences, "hans@peter.com","12344")).isFalse();
    }

    @Test
    public void should_validate_email_address(){
        assertThat(LoginValidator.isLoginEmailValid("1@2.3")).isFalse();
        assertThat(LoginValidator.isLoginEmailValid("hans@mueller.com")).isTrue();
        assertThat(LoginValidator.isLoginValid(sharedPreferences, "lorris@goyvaerts.com", "L2L2L2a%")).isFalse();
    }
}
