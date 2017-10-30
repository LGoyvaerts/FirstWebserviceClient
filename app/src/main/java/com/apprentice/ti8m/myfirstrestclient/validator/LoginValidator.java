package com.apprentice.ti8m.myfirstrestclient.validator;

import com.apprentice.ti8m.myfirstrestclient.api.APIInterface;
import com.apprentice.ti8m.myfirstrestclient.model.User;

import org.reactivestreams.Subscriber;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by gol on 10.10.17.
 */

public class LoginValidator {

    private static final String EMAIL_PATTERN =
            "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                    + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    private static final String PASSWORD_PATTERN = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{6,20})";

    private static Pattern pattern;
    private static Matcher matcher;
    private static Boolean isLValid = false;
    private static Boolean isSValid = false;


    public static void setIsSValid(Boolean isSValid) {
        LoginValidator.isSValid = isSValid;
    }


    public static void setIsLValid(Boolean LValid) {
        isLValid = LValid;
    }

    public static void isLoginValid(String email, String password, Callback<Void> callback) {
        String encryptedPassword = LoginValidator.encryptStringToSHA256(password);

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();


        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.10.36.64:8080/pizzashop/")
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        final APIInterface request = retrofit.create(APIInterface.class);
        Call<Void> call = request.getUser(new User(email, encryptedPassword));
        call.enqueue(callback);
    }

    public static void isSignupValid(String email, String password, String confirmPassword, Callback<Void> callback) {
        String encryptedPassword = LoginValidator.encryptStringToSHA256(password);
        if (isLoginEmailValid(email) && isBothPasswordsValid(password, confirmPassword)) {
            HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
            OkHttpClient okHttpClient = new OkHttpClient
                    .Builder()
                    .addInterceptor(httpLoggingInterceptor)
                    .build();


            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://10.10.36.64:8080/pizzashop/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            final APIInterface request = retrofit.create(APIInterface.class);
            Call<Void> call = request.createUser(new User(email, encryptedPassword));
            call.enqueue(callback);

        }
    }

    public static Boolean isLoginEmailValid(String email) {
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static Boolean isLoginPasswordValid(String password) {
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);
        return (matcher.matches());
    }

    public static Boolean isBothPasswordsValid(String password, String confirmPasword) {
        return isLoginPasswordValid(password) && password.equals(confirmPasword);
    }

    public static String encryptStringToSHA256(String toEncrypt) {
        String encryptet = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(toEncrypt.getBytes("UTF-8"));
            byte[] encrptedHash = md.digest();
            StringBuffer sb = new StringBuffer();
            for (byte anEncrptedHash : encrptedHash) {
                sb.append(Integer.toHexString(0xFF & anEncrptedHash));
            }
            encryptet = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptet;
    }

}
