package com.apprentice.ti8m.myfirstrestclient.api;

import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.apprentice.ti8m.myfirstrestclient.model.User;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by gol on 26.09.17.
 * Don't copy my Stuff!
 */

public class APIClient implements APIInterface{

    private static APIClient INSTANCE = null;
    private APIInterface client;
    private APIClient(){
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient okHttpClient = new OkHttpClient
                .Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();


        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.baseUrl)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        client = retrofit.create(APIInterface.class);
    }

    public static synchronized APIClient getInstance(){
        if(INSTANCE == null) {
            INSTANCE = new APIClient();
        }
        return INSTANCE;
    }

    @Override
    public Call<List<Pizza>> getPizzas() {
        return client.getPizzas();
    }

    @Override
    public Call<Void> createUser(User body) {
        return client.createUser(body);
    }

    @Override
    public Call<Void> getUser(User body) {
        return client.getUser(body);
    }
}
