package com.apprentice.ti8m.myfirstrestclient;

import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

import java.util.List;

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


}
