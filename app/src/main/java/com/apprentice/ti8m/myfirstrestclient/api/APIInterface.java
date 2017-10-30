package com.apprentice.ti8m.myfirstrestclient.api;

import com.apprentice.ti8m.myfirstrestclient.model.Pizza;
import com.apprentice.ti8m.myfirstrestclient.model.User;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by gol on 26.09.17.
 */

public interface APIInterface {

    @GET("api/rest/pizzas")
    Call<List<Pizza>> getPizzas();

    @POST("api/rest/users/create")
    Call<Void> createUser(@Body User body);

    @POST("api/rest/users/isuservalid")
    Call<Void> getUser(@Body User body);

}
