package com.apprentice.ti8m.myfirstrestclient;

import com.apprentice.ti8m.myfirstrestclient.model.JSONResponse;
import com.apprentice.ti8m.myfirstrestclient.model.Pizza;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by gol on 26.09.17.
 */

public interface APIInterface {

    @GET("api/rest/pizzas")
    Call<List<Pizza>> getPizzas();

    @GET("api/rest/pizzas")
    Call<JSONResponse> getJSON();


}
